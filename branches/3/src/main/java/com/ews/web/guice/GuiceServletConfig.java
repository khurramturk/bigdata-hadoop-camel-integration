package com.ews.web.guice;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ews.web.guice.module.FileReaderModule;
import com.ews.web.guice.module.MongoModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceServletConfig extends AbstractGuiceServletConfig {

	@Override
	protected Injector getInjector() {
		String host = null;
        Integer port = null;
        String dbName = null;
        String notificationsHost = null;
        Integer notificationsPort = null;
        String notificationsPath = null;
        String notificationsTargetDBPath = null;
        Boolean autoConnectRetry = null;
        Integer connectionsPerHost = null;
        Integer connectTimeout = null;
        Integer maxWaitTime = null;
        Integer socketTimeout = null;
        Integer threadsAllowedToBlockForConnectionMultiplier = null;
        Integer w = null;
        Boolean fsync = null;
        Boolean j = null;
        Boolean cursorFinalizerEnabled = null;
        Integer maxAutoConnectRetryTime = null;
        Boolean safe = null;
        Boolean socketKeepAlive = null;
        Integer wtimeout = null;
        Properties props = new Properties();
        InitialContext initContext;
        try {
            initContext = new InitialContext();
            host = (String) initContext.lookup("java:comp/env/fileArchiveHost");
            port = (Integer) initContext.lookup("java:comp/env/fileArchivePort");
            dbName = (String) initContext.lookup("java:comp/env/fileArchiveDatabaseName");
            notificationsHost = (String) initContext.lookup("java:comp/env/nHost");
            notificationsPort = (Integer) initContext.lookup("java:comp/env/nPort");
            notificationsPath = (String) initContext.lookup("java:comp/env/nPath");
            notificationsTargetDBPath = (String) initContext.lookup("java:comp/env/nTargetDBPath");
            autoConnectRetry = (Boolean) initContext.lookup("java:comp/env/autoConnectRetry");
            connectionsPerHost = (Integer) initContext.lookup("java:comp/env/connectionsPerHost");
            connectTimeout = (Integer) initContext.lookup("java:comp/env/connectTimeout");
            maxWaitTime = (Integer) initContext.lookup("java:comp/env/maxWaitTime");
            socketTimeout = (Integer) initContext.lookup("java:comp/env/socketTimeout");
            threadsAllowedToBlockForConnectionMultiplier = (Integer) initContext
                .lookup("java:comp/env/threadsAllowedToBlockForConnectionMultiplier");
            w = (Integer) initContext.lookup("java:comp/env/w");
            fsync = (Boolean) initContext.lookup("java:comp/env/fsync");
            j = (Boolean) initContext.lookup("java:comp/env/j");
            cursorFinalizerEnabled = (Boolean) initContext.lookup("java:comp/env/cursorFinalizerEnabled");
            maxAutoConnectRetryTime = (Integer) initContext.lookup("java:comp/env/maxAutoConnectRetryTime");
            safe = (Boolean) initContext.lookup("java:comp/env/safe");
            socketKeepAlive = (Boolean) initContext.lookup("java:comp/env/socketKeepAlive");
            wtimeout = (Integer) initContext.lookup("java:comp/env/wtimeout");
            System.out.println("FILE ARCHIVE HOST: " + host + ":" + port);
            props.setProperty("org.mongo.MongoDB.fileArchiveHost", host);
            props.setProperty("org.mongo.MongoDB.fileArchivePort", String.valueOf(port));
            props.setProperty("org.mongo.MongoDB.fileArchiveDatabaseName", dbName);
            props.setProperty("notifications-host", notificationsHost);
            props.setProperty("notifications-port", String.valueOf(notificationsPort));
            props.setProperty("notifications-path", notificationsPath);
            props.setProperty("notifications-targetDB-path", String.valueOf(notificationsTargetDBPath));
            props.setProperty("org.mongo.MongoDB.autoConnectRetry", String.valueOf(autoConnectRetry));
            props.setProperty("org.mongo.MongoDB.connectionsPerHost", String.valueOf(connectionsPerHost));
            props.setProperty("org.mongo.MongoDB.connectTimeout", String.valueOf(connectTimeout));
            props.setProperty("org.mongo.MongoDB.maxWaitTime", String.valueOf(maxWaitTime));
            props.setProperty("org.mongo.MongoDB.socketTimeout", String.valueOf(socketTimeout));
            props.setProperty("org.mongo.MongoDB.threadsAllowedToBlockForConnectionMultiplier",
                String.valueOf(threadsAllowedToBlockForConnectionMultiplier));
            props.setProperty("org.mongo.MongoDB.w", String.valueOf(w));
            props.setProperty("org.mongo.MongoDB.fsync", String.valueOf(fsync));
            props.setProperty("org.mongo.MongoDB.j", String.valueOf(j));
            props.setProperty("org.mongo.MongoDB.cursorFinalizerEnabled", String.valueOf(cursorFinalizerEnabled));
            props.setProperty("org.mongo.MongoDB.maxAutoConnectRetryTime", String.valueOf(maxAutoConnectRetryTime));
            props.setProperty("org.mongo.MongoDB.safe", String.valueOf(safe));
            props.setProperty("org.mongo.MongoDB.socketKeepAlive", String.valueOf(socketKeepAlive));
            props.setProperty("org.mongo.MongoDB.wtimeout", String.valueOf(wtimeout));
            props.setProperty("cronString", "0/5 * * * * ?");
            props.setProperty("numDays", "5");

            Injector injector = Guice.createInjector(new FileReaderModule(getCamelContext()), new MongoModule(props));

            return injector;
        } catch (NamingException e) {
            System.out.println("Error looking up server resources for file archive!");
            e.printStackTrace();
            return null;
        }
	}

}
