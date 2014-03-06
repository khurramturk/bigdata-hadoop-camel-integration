package com.ews.enterprise.integration.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class ImportHandler {

    private Importer importer;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportHandler.class);

    @Inject
    public ImportHandler(final Importer importer) {
        this.importer = importer;
    }

    public final ChunkAndResponseWrapper importFile(final FileSubmission fileSubmission)
        throws FileProcessingException {
    	ChunkAndResponseWrapper wrapper;
        try {
            wrapper = importer.importFile(fileSubmission);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FileProcessingException("Error when importing file", e, fileSubmission.getContext());
        }
        return wrapper;
    }

    public final ChunkAndResponseWrapper createWrapper(final List<Chunk> chunks)
        throws FileProcessingException {
    	ChunkAndResponseWrapper wrapper;
        try {
            wrapper = new ChunkAndResponseWrapper(chunks);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new FileProcessingException("Error when creating wrapper during Import", e, chunks.get(0)
                .getContext());
        }
        return wrapper;
    }

    /**
     * @return the importer
     */
    public final Importer getImporter() {
        return importer;
    }

    /**
     * @param importer
     *            the importer to set
     */
    public final void setImporter(final Importer importer) {
        this.importer = importer;
    }
}
