package com.ews.web.guice.module;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.integration.dao.FileStorageDAO;
import com.ews.enterprise.integration.dao.FileStorageDAOMongoImpl;
import com.ews.enterprise.integration.service.FileStorageService;
import com.ews.enterprise.integration.service.FileStorageServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongoModule extends AbstractModule {
    private final Properties properties;
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoModule.class);

    public MongoModule(final Properties properties) {
        this.properties = properties;
    }
    //CHECKSTYLE:OFF MethodLength
    @Provides
    @Singleton
    final Mongo provideMongoConnection() throws Throwable {

        // get host and port
        String host = properties.getProperty("org.mongo.MongoDB.fileArchiveHost");
        int port = Integer.parseInt(properties.getProperty("org.mongo.MongoDB.fileArchivePort"));

        // configuration options
        // using guidance from
        // http://stackoverflow.com/questions/6520439/
        // how-to-configure-mongodb-java-driver-mongooptions-for-production-use
        // and doc from http://api.mongodb.org/java/2.11.0/
        MongoOptions mo = new MongoOptions();

        // primary
        // If true, the driver will keep trying to connect to the same server in
        // case that the socket cannot be established
        mo.setAutoConnectRetry(new Boolean(properties.getProperty("org.mongo.MongoDB.autoConnectRetry")));
        // The maximum number of connections allowed per host for this Mongo
        // instance
        mo.setConnectionsPerHost(Integer.parseInt(properties.getProperty("org.mongo.MongoDB.connectionsPerHost")));
        // the connection timeout in milliseconds
        mo.setConnectTimeout(Integer.parseInt(properties.getProperty("org.mongo.MongoDB.connectTimeout")));
        // The maximum wait time in milliseconds that a thread may wait for a
        // connection to become available
        mo.setMaxWaitTime(Integer.parseInt(properties.getProperty("org.mongo.MongoDB.maxWaitTime")));
        // The socket timeout in milliseconds It is used for I/O socket read and
        // write operations Socket.setSoTimeout(int) Default is 0 and means no
        // timeout
        mo.setSocketTimeout(Integer.parseInt(properties.getProperty("org.mongo.MongoDB.socketTimeout")));
        // this multiplier, multiplied with the connectionsPerHost setting,
        // gives the maximum number of threads that may be waiting for a
        // connection to become available from the pool
        mo.setThreadsAllowedToBlockForConnectionMultiplier(Integer.parseInt(properties
            .getProperty("org.mongo.MongoDB.threadsAllowedToBlockForConnectionMultiplier")));
        // the "w" value, (number of writes), of the global WriteConcern
        mo.setW(Integer.parseInt(properties.getProperty("org.mongo.MongoDB.w")));
        // The "fsync" value of the global WriteConcern
        mo.setFsync(new Boolean(properties.getProperty("org.mongo.MongoDB.fsync")));
        // The "j" value of the global WriteConcern
        mo.setJ(new Boolean(properties.getProperty("org.mongo.MongoDB.j")));

        // secondary
        // Sets whether there is a a finalize method created that cleans up
        // instances of DBCursor that the client does not close
        mo.setCursorFinalizerEnabled(new Boolean(properties.getProperty("org.mongo.MongoDB.cursorFinalizerEnabled")));
        // The maximum amount of time in MS to spend retrying to open connection
        // to the same server
        mo.setMaxAutoConnectRetryTime(Integer.parseInt(properties
            .getProperty("org.mongo.MongoDB.maxAutoConnectRetryTime")));
        // If true the driver will use a WriteConcern of WriteConcern.SAFE for
        // all operations
        mo.setSafe(new Boolean(properties.getProperty("org.mongo.MongoDB.safe")));
        // This flag controls the socket keep alive feature that keeps a
        // connection alive through firewalls
        mo.setSocketKeepAlive(new Boolean(properties.getProperty("org.mongo.MongoDB.socketKeepAlive")));
        // The "wtimeout" value of the global WriteConcern
        mo.setWtimeout(Integer.parseInt(properties.getProperty("org.mongo.MongoDB.wtimeout")));

        // try connection
        try {
            ServerAddress sa = new ServerAddress(host, port);
            return new Mongo(sa, mo);
        } catch (Exception ex) {
            // If this fails, no point in continuing
            LOGGER.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
    //CHECKSTYLE:ON MethodLength

    @Override
    protected final void configure() {
        bindConstant().annotatedWith(Names.named("FileArchiveDBName"))
            .to(properties.getProperty("org.mongo.MongoDB.fileArchiveDatabaseName"));
        bind(FileStorageService.class).to(FileStorageServiceImpl.class);
        bind(FileStorageDAO.class).to(FileStorageDAOMongoImpl.class);
    }
}
