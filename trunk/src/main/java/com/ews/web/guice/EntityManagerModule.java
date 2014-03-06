package com.ews.web.guice;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.datacollection.dao.EntityManagerProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class EntityManagerModule extends AbstractModule {

    private final DataSource dataSource;
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityManagerModule.class);

    public EntityManagerModule(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected final void configure() {
        bind(EntityManager.class).toProvider(EntityManagerProvider.class);
    }

    @Provides
    final EntityManagerFactory providesEntityManagerFactory() throws Throwable {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("openjpa.ConnectionFactory", dataSource);
        // Create entity manager factory
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("ews-entity-dao", props);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }
        return emf;
    }
}
