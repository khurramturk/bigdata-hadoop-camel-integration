package com.ews.enterprise.datacollection.guice;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.datacollection.camel.TraceMessageProcessor;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class DataCollectionJpaModule extends AbstractModule {
    private Properties properties;
    private static final Logger LOGGER = LoggerFactory.getLogger(DataCollectionJpaModule.class);

    public DataCollectionJpaModule(final Properties p) {
        this.properties = p;
    }

    @Provides
    final Properties providesProperties() throws Throwable {
        return properties;
    }

    @Override
    protected final void configure() {
        this.requestStaticInjection(TraceMessageProcessor.class);
    }
}
