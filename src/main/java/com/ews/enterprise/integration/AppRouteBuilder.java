package com.ews.enterprise.integration;


import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.integration.service.FileSubmission;
import com.ews.enterprise.integration.service.ImportHandler;
import com.google.inject.Inject;

public class AppRouteBuilder extends RouteBuilder {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppRouteBuilder.class);
	private final StorageHandler storageHandler;
	private final ImportHandler importHandler;
	private final Properties properties;
	@Inject
	public AppRouteBuilder(final StorageHandler store, final ImportHandler importHandler, final Properties properties) {
		this.storageHandler = store;
		this.importHandler = importHandler;
		this.properties = properties;
	}

	@Override
	public void configure() throws Exception {
		fileSaveRout();
		importFileRoute();
		from("seda:storage")
        .tracing()
        .bean(this.storageHandler, "saveRecordsInCanonicalModel");
	}

	private void fileSaveRout() {
		from("activemq:httpinfile").tracing()
				.bean(this.storageHandler, "saveOriginalFile")
				.to("seda:import");
		
		from("activemq:ftpinfile").tracing()
		.bean(this.storageHandler, "saveOriginalFile")
		.to("seda:import");
	}
	
	private void importFileRoute() {
        // Importing the file
        from("seda:import").handleFault()
            .tracing()
            .process(new Processor() {
                @Override
                public void process(final Exchange exchange) throws Exception {
                    LOGGER.info("Import File Route INITIALIZED");
                }
            })
            .bean(this.importHandler, "importFile")
            .multicast()
            .parallelProcessing()
            .to("seda:storage", "seda:imported");
    }
}
