package com.ews.web.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean(name="fileUploadController")
public class FileUploadController {
	 private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
	public FileUploadController() {
    }
	
	@PostConstruct
	public final void init(){
		LOGGER.info("this is my controller");
	}
	public final void handleFileUpload(final FileUploadEvent event){
		LOGGER.debug("Uploading file: {}" + event.getFile().getFileName());
		
        LOGGER.info(""+event.getFile().getContents());
	}
}
