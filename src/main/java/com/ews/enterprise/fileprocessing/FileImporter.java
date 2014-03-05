package com.ews.enterprise.fileprocessing;

import java.io.IOException;
import java.io.InputStream;

public interface FileImporter {

    void parse(InputStream in, RecordHandler handler) throws IOException, FileImportException;

}
