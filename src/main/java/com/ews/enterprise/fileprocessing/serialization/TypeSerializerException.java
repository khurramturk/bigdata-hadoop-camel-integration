package com.ews.enterprise.fileprocessing.serialization;
/**
 * @author rana.ijaz
 */
public class TypeSerializerException extends Exception {

	private static final long serialVersionUID = -5635833107201915838L;

	public TypeSerializerException() {
        super();
    }

    public TypeSerializerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TypeSerializerException(final String message) {
        super(message);
    }

    public TypeSerializerException(final Throwable cause) {
        super(cause);
    }

}
