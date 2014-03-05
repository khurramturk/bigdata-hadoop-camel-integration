package com.ews.enterprise.fileprocessing;

public class FileImportException extends Exception {

	private static final long serialVersionUID = 1570619386878766355L;

	public FileImportException() {
        super();
    }

    public FileImportException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FileImportException(final String message) {
        super(message);
    }

    public FileImportException(final Throwable cause) {
        super(cause);
    }

}
