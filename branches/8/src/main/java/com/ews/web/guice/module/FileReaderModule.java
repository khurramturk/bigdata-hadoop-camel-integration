package com.ews.web.guice.module;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.ProxyBuilder;

import com.ews.enterprise.integration.service.FileReaderService;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class FileReaderModule extends AbstractModule {
    private final CamelContext camelContext;

    public FileReaderModule(final CamelContext camelContext) {
        super();
        this.camelContext = camelContext;
    }
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    final FileReaderService provideFileUpload() {
        try {
            return new ProxyBuilder(camelContext)
                .endpoint("direct:start")
                .build(FileReaderService.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
