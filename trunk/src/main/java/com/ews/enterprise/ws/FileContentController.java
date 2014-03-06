package com.ews.enterprise.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/fileContentController")
public class FileContentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileContentController.class);

	@GET
	@Path("/processFileContent/{feildId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public final String processFileContent(@PathParam("feildId") String feildId)
			throws Exception {

		LOGGER.info(">>> Service Called <<< " + feildId);
		return "Service Called " + feildId;
	}
}
