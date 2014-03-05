package com.ews.enterprise.integration;

import com.ews.enterprise.fileprocessing.FileTemplate;

public class FileProcessingContext {
	private FileTemplate importTemplate;
    private FileTemplate exportTemplate;
	private String fileName;
	private String externalFileID;

	public final String getFileName() {
		return fileName;
	}

	public final void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public final String getExternalFileID() {
		return externalFileID;
	}

	public final void setExternalFileID(final String externalFileID) {
		this.externalFileID = externalFileID;
	}
	
	public final FileTemplate getImportTemplate() {
        return importTemplate;
    }

    public final void setImportTemplate(final FileTemplate importTemplate) {
        this.importTemplate = importTemplate;
    }
}
