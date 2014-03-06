package com.ews.enterprise.integration.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.fusesource.hawtbuf.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ews.enterprise.fileprocessing.DefaultFileImporter;
import com.ews.enterprise.fileprocessing.ExceptionDetail;
import com.ews.enterprise.fileprocessing.FileImportException;
import com.ews.enterprise.fileprocessing.FileImporter;
import com.ews.enterprise.fileprocessing.Record;
import com.ews.enterprise.fileprocessing.RecordHandler;
import com.ews.enterprise.integration.FileProcessingContext;
import com.google.inject.Inject;

public class Importer {

    private final class ImportRecordHandler implements RecordHandler {
        private final List<Chunk> chunks;
        private final FileProcessingContext context;
        private final List<Record> buffer = new LinkedList<Record>();

        private ImportRecordHandler(final List<Chunk> chunks,
            final FileProcessingContext context) {
            this.chunks = chunks;
            this.context = context;
        }

        @Override
        public void handleRecord(final Record record) throws FileImportException {
            buffer.add(record);
            if (buffer.size() == maxBufferSize) {
                addChunk();
                buffer.clear();
            }
        }

        @Override
        public void error(final ExceptionDetail ex) {
            if (ex != null) {
                System.out.println("Exception occurred for Row: " + ex.getRowPosition() + "Column: "
                    + ex.getCanonicalField() + ex.getErrorMsg());
            }
        }

        @Override
        public void eof() throws FileImportException {
            if (!buffer.isEmpty()) {
                addChunk();
            }
        }

        private void addChunk() {
            Chunk chunk = new Chunk(new LinkedList<Record>(buffer), context);
            chunks.add(chunk);
        }
    }

    private int maxBufferSize;
    private static final Logger LOGGER = LoggerFactory.getLogger(Importer.class);

    @Inject
    public Importer(final Properties props) {
        this.maxBufferSize = Integer.parseInt(props.getProperty("org.integration.fileChunkSize"));
    }

    public final ChunkAndResponseWrapper importFile(final FileSubmission fileSubmission) throws IOException,
        FileImportException {
        FileImporter importer = new DefaultFileImporter(fileSubmission.getContext().getImportTemplate());
        final List<Chunk> chunks = new LinkedList<Chunk>();
        
        FileProcessingContext fileContext = fileSubmission.getContext();
        fileContext.setImportTemplate(fileContext.getImportTemplate().getNormalizedFileTemplate());
        importer.parse((InputStream) new ByteArrayInputStream(fileSubmission.getFileContents()),
            new ImportRecordHandler(chunks, fileContext));

        ChunkAndResponseWrapper wrapper = new ChunkAndResponseWrapper(chunks);

        return wrapper;
    }
}
