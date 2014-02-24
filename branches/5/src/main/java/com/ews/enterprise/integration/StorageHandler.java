package com.ews.enterprise.integration;

import java.util.Properties;

import com.ews.enterprise.integration.service.FileStorageService;
import com.ews.enterprise.integration.service.FileSubmission;
import com.google.inject.Inject;

public class StorageHandler {
	private FileStorageService fileStorageService = null;

    @Inject
    public StorageHandler(final FileStorageService fileStorageService, final Properties props) {
        this.fileStorageService = fileStorageService;
    }
    
    public final void saveOriginalFile(final FileSubmission file) throws Exception {
        try {
            this.fileStorageService.saveFileAsIs(file);
        } catch (Exception e) {
            throw new Exception("Error when saving original file in Mongo", e);
        }
    }
}
