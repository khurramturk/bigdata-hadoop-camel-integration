package com.ews.enterprise.integration.service;

import com.ews.enterprise.integration.FileProcessingContext;

public class FileSubmission {
	private byte[] fileContents;
	private FileProcessingContext context;

	public final byte[] getFileContents() {
		return (byte[]) fileContents.clone();
	}

	public final void setFileContents(final byte[] fileContents) {
		this.fileContents = (byte[]) fileContents.clone();
	}

	public final FileProcessingContext getContext() {
		return context;
	}

	public final void setContext(final FileProcessingContext context) {
		this.context = context;
	}
}
