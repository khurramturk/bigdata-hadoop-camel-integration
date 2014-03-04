package com.ews.enterprise.fileprocessing;

import java.io.Serializable;

public class Field implements Serializable {

	private static final long serialVersionUID = 2735844267573924197L;
	private final String fieldName;
    private final String description;

    public Field(final String fieldName) {
        this(fieldName, fieldName);
    }

    public Field(final String fieldName, final String description) {
        this.fieldName = fieldName;
        this.description = description;
    }

    public final String getFieldName() {
        return fieldName;
    }

    public final String getDescription() {
        return description;
    }

}
