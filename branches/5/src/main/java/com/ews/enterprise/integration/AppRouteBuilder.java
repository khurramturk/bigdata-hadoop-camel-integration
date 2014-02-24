package com.ews.enterprise.integration;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.ews.enterprise.integration.service.FileSubmission;
import com.google.inject.Inject;

public class AppRouteBuilder extends RouteBuilder {
	private final StorageHandler storageHandler;

	@Inject
	public AppRouteBuilder(final StorageHandler store) {
		this.storageHandler = store;
	}

	@Override
	public void configure() throws Exception {
		fileReadRoute();
		fileSaveRout();
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
}
