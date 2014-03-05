package com.ews.enterprise.fileprocessing;

public interface RecordHandler {

    void handleRecord(Record record) throws FileImportException;

    void eof() throws FileImportException;

    // TODO Pass error details and position
    void error(ExceptionDetail e);

}
