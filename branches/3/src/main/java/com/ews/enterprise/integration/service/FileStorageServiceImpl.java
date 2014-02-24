package com.ews.enterprise.integration.service;

import com.ews.enterprise.integration.FileProcessingContext;
import com.ews.enterprise.integration.dao.FileStorageDAO;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;

public class FileStorageServiceImpl implements FileStorageService {
	private FileStorageDAO dao = null;

	@Inject
	public FileStorageServiceImpl(final FileStorageDAO dao) {
		this.dao = dao;
	}

	@Override
	public final void saveFileAsIs(final FileSubmission fileSubmission)
			throws Exception {
		if (fileSubmission != null) {
			final FileProcessingContext context = fileSubmission.getContext();
			if (context != null) {
				dao.saveFile(fileSubmission.getFileContents(),
						context.getFileName(), buildMetaData(context));
			}
		}
	}
	
	private BasicDBObject buildMetaData(final FileProcessingContext context) {
        final BasicDBObject metaData = new BasicDBObject();
        metaData.append("EXTERNAL_FILE_ID", context.getExternalFileID());
        metaData.append("FILE_NAME", context.getFileName());
        return metaData;
    }
}
