package com.ews.enterprise.datacollection;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.datacollection.mongo.MongoDateFormatter;
import com.ews.enterprise.datacollection.orm.mongo.MongoBotDetailDcr;
import com.ews.enterprise.datacollection.orm.mongo.MongoBotSummaryDcr;
import com.ews.enterprise.datacollection.orm.mongo.MongoCamelDcr;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

public class DataCollectionServiceMongoImpl implements DataCollectionService {

    private boolean botOn = false;
    private boolean camelOn = false;
    // properties
    private Properties props = new Properties();
    // mongo params
    private Mongo mongoConnection;
    private DB mongoDb;
    private DBCollection mongoCollection;
    private static SimpleDateFormat mongoFormat = MongoDateFormatter.getDateFormatter();
    private String mongoDatabaseString;
    private String mongoCollectionString;
    private WriteConcern mongoWC;
    private Gson gson;
    private static final Logger LOGGER =
        LoggerFactory.getLogger(DataCollectionServiceMongoImpl.class);

    // creation
    @Inject
    public DataCollectionServiceMongoImpl(final Mongo m, final Properties p) {
        mongoConnection = m;
        props = p;
        init();
    }

    private void init() {
        try {
            String botOnString = props
                .getProperty("org.airedale.dc.bot");
            if ("ON".equals(botOnString)) {
                botOn = true;
            }
            String camelOnString = props
                .getProperty("org.airedale.dc.camel");
            if ("ON".equals(camelOnString)) {
                camelOn = true;
            }
            if (botOn || camelOn) {
                GsonBuilder gsb = new GsonBuilder();
                gsb.registerTypeAdapter(Date.class, new DateTimeSerializer());
                gsb.registerTypeAdapter(Date.class, new DateTimeDeserializer());
                gson = gsb.create();
            }
        } catch (Exception e) {
            LOGGER.error("Error during DataCollectionServiceImpl ", e);
        }
    }

    // interface methods
    @Override
    public final Object getDcr(final String type) {
        Object dcr = null;

        if ("BOTSUMMARY".equals(type)) {
            dcr = new MongoBotSummaryDcr();
            return dcr;
        }
        if ("BOTDETAIL".equals(type)) {
            dcr = new MongoBotDetailDcr();
            return dcr;
        }
        if ("CAMEL".equals(type)) {
            dcr = new MongoCamelDcr();
            return dcr;
        }
        LOGGER.warn("No Dcr type matched input string: " + type);
        return dcr;
    }

    // save
    @Override
    public final boolean save(final Object dcr) {
        boolean result = false;

        try {
            if (((dcr instanceof MongoBotSummaryDcr) && botOn)
                || ((dcr instanceof MongoBotDetailDcr) && botOn)
                || ((dcr instanceof MongoBotSummaryDcr) && botOn)
                || ((dcr instanceof MongoCamelDcr) && camelOn)) {
                // translate to json
                String objectString = gson.toJson(dcr);
                BasicDBObject mongoObject = (BasicDBObject) JSON
                    .parse(objectString);
                // setup connection parameters appropriate for type
                mongoConnect(dcr);
                // transmit
                mongoCollection.save(mongoObject, mongoWC);
                // mark success
                result = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error during data collection record save", e);
        }

        return result;
    }

    //CHECKSTYLE:OFF CyclomaticComplexity
    @Override
    public final boolean saveAll(final List<Object> dcrList) {
        boolean result = false;
        try {
            if (((dcrList.get(0) instanceof MongoBotSummaryDcr) && botOn)
                || ((dcrList.get(0) instanceof MongoBotDetailDcr) && botOn)
                || ((dcrList.get(0) instanceof MongoBotSummaryDcr) && botOn)
                || ((dcrList.get(0) instanceof MongoCamelDcr) && camelOn)) {
                //CHECKSTYLE:ON
                // array of translated objects
                DBObject[] objectArray = new DBObject[dcrList.size()];

                // translate java objects to json
                int i = 0;
                for (Object dcr : dcrList) {
                    String objectString = gson.toJson(dcr);
                    BasicDBObject mongoObject = (BasicDBObject) JSON
                        .parse(objectString);
                    objectArray[i++] = (DBObject) mongoObject;
                }
                // open connection
                mongoConnect(dcrList.get(0));
                // transmit
                mongoCollection.insert(objectArray, mongoWC);
                // mark success
                result = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error during data collection record save", e);
        }

        return result;
    }

    // private methods

    private void mongoConnect(final Object dcrRecord) throws Exception {
        // mongo
        mongoDatabaseString = props.getProperty("org.airedale.dc.mongo.database");
        mongoWC = this.convertToWriteConcern(props.getProperty("org.airedale.dc.mongo.writeConcern"));
        mongoCollectionString = null;
        if (dcrRecord instanceof MongoBotSummaryDcr) {
            mongoCollectionString = props.getProperty("org.airedale.dc.mongo.summaryBotCollection");
        }
        if (dcrRecord instanceof MongoBotDetailDcr) {
            mongoCollectionString = props.getProperty("org.airedale.dc.mongo.detailedBotCollection");
        }
        if (dcrRecord instanceof MongoCamelDcr) {
            mongoCollectionString = props.getProperty("org.airedale.dc.mongo.camelCollection");
        }
        if (mongoCollectionString == null) {
            throw new Exception("No collection for " + dcrRecord.toString());
        }
        // set up mongo connection
        // set database
        mongoDb = mongoConnection.getDB(mongoDatabaseString);
        // set collection
        mongoCollection = mongoDb.getCollection(mongoCollectionString);
    }

    private WriteConcern convertToWriteConcern(final String s) {
        WriteConcern wc = WriteConcern.valueOf(s);
        return wc;
    }

    // serialize / deserialize methods
    private static class DateTimeSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(final Date src, final Type typeOfSrc,
            final JsonSerializationContext context) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("$date", mongoFormat.format(src));
            return jsonObject;
        }
    }

    private static class DateTimeDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(final JsonElement json, final Type typeOfT,
            final JsonDeserializationContext context) {
            Date returnedDate = null;
            try {
                returnedDate = mongoFormat.parse(json.getAsJsonObject().get("$date").getAsString());
            } catch (Exception e) {
                LOGGER.error("Error during date time deserialization", e);
            }
            return returnedDate;
        }
    }
}

