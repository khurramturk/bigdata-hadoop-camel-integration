package com.ews.enterprise.datacollection.guice;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.datacollection.DataCollectionServiceMongoImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.mongodb.Mongo;

public class DataCollectionMongoModule extends AbstractModule {
    private Properties properties;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectionMongoModule.class);

    public DataCollectionMongoModule(final Properties p) {
        this.properties = p;
    }

    @Provides
    @Singleton
    final DataCollectionServiceMongoImpl provideDataCollection(final Mongo mongo) throws Throwable {
        DataCollectionServiceMongoImpl dcsmi = null;
        dcsmi = new DataCollectionServiceMongoImpl(mongo, properties);
        return dcsmi;
    }

    @Override
    protected final void configure() {
        //bind(DataCollectionService.class).to(DataCollectionServiceMongoImpl.class);
    }
}
