package com.ews.enterprise.integration;


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
	@Inject
	public AppRouteBuilder(final StorageHandler store, final ImportHandler importHandler) {
		this.storageHandler = store;
		this.importHandler = importHandler;
	}

	@Override
	public void configure() throws Exception {
		fileReadRoute();
		fileSaveRout();
		importFileRoute();
		from("seda:storage")
        .tracing()
        .bean(this.storageHandler, "saveRecordsInCanonicalModel");
	}

	/**
	 * This method routes the file read through web, extracts the filename on
	 * camelHeader and pushes for Archive Input is FileSubmission Output is
	 * FileSubmission to Archive
	 */
	private void fileReadRoute() {
		from("activemq:infile").tracing().process(new Processor() {
			@Override
			public void process(final Exchange exchange) throws Exception {
				String fileId = exchange.getIn().getBody(FileSubmission.class).getContext().getExternalFileID();
				exchange.getIn().setHeader("CamelFileName", exchange.getIn().getBody(FileSubmission.class).getContext().getFileName());				
				exchange.getIn().setHeader("CamelExternalFileID", fileId);
			}
		}).to("seda:archive");
	}

	private void fileSaveRout() {
		from("seda:archive").tracing()
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
