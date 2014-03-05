package com.ews.enterprise.integration;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.integration.service.Chunk;
import com.ews.enterprise.integration.service.ChunkAndResponseWrapper;
import com.ews.enterprise.integration.service.FileProcessingException;
import com.ews.enterprise.integration.service.FileStorageService;
import com.ews.enterprise.integration.service.FileSubmission;
import com.google.inject.Inject;

public class StorageHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageHandler.class);
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
    
    public final void saveRecordsInCanonicalModel(final ChunkAndResponseWrapper wrapper)
            throws FileProcessingException {
            try {
                List<Chunk> chunks = new ArrayList<Chunk>(wrapper.getListChunk());
                for (Chunk chunk : chunks) {
                    this.fileStorageService.insertRecords(chunk.getRecords(), chunk.getContext());
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                throw new FileProcessingException("Error when saving original file Records in Mongo", e, wrapper
                    .getListChunk().get(0).getContext());
            }
        }
}
