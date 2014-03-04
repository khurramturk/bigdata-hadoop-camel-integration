package com.ews.enterprise.integration.service;

import java.util.List;

import com.ews.enterprise.fileprocessing.Record;
import com.ews.enterprise.integration.FileProcessingContext;

public interface FileStorageService {
	void saveFileAsIs(FileSubmission fileSubmission) throws Exception;
	void insertRecords(final List<Record> recordList, final FileProcessingContext context)throws Exception;
}
