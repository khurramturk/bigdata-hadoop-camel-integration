package com.ews.enterprise.integration.service;

import com.ews.enterprise.integration.FileProcessingContext;

public class FileProcessingException extends Exception {

	private static final long serialVersionUID = 6750558784353087761L;
	private FileProcessingContext context;

    public FileProcessingException() {
        super();
    }

    public FileProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FileProcessingException(final String message, final Throwable cause, final FileProcessingContext context) {
        super(message, cause);
        this.context = context;
    }

    public FileProcessingException(final String message) {
        super(message);
    }

    public FileProcessingException(final Throwable cause) {
        super(cause);
    }

    public final FileProcessingContext getContext() {
        return context;
    }

    public final void setContext(final FileProcessingContext context) {
        this.context = context;
    }

}
