package com.ews.enterprise.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/readFileContentWebService")
public class ReadFileContentWebService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReadFileContentWebService.class);

	@GET
	@Path("/readFileContent/{feildID}")
	// @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public final String readFileContent(@PathParam("feildID") String feildID)
			throws Exception {

		System.out.println(">>> Service Called <<< " + feildID);
		return "Service Called " + feildID;
	}

	
}
