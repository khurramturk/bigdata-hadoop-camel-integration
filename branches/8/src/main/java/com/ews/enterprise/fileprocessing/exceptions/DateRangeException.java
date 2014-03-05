package com.ews.enterprise.fileprocessing.exceptions;

public class DateRangeException extends Exception {

	private static final long serialVersionUID = -5174010600225166277L;

	public DateRangeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DateRangeException(final String message) {
        super(message);
    }

    public DateRangeException(final Throwable cause) {
        super(cause);
    }

}
