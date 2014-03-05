package com.ews.enterprise.fileprocessing;

import java.io.Serializable;

public class Column implements Serializable {

	private static final long serialVersionUID = 7260552106629547429L;
	private String canonicalName;
    private Object value;
    private int columnposition;
    private boolean inValidValue;

    public final String getCanonicalName() {
        return canonicalName;
    }

    public final void setCanonicalName(final String canonicalName) {
        this.canonicalName = canonicalName;
    }

    public final Object getValue() {
        return value;
    }

    public final void setValue(final Object value) {
        this.value = value;
    }

    public final int getColumnposition() {
        return columnposition;
    }

    public final void setColumnposition(final int columnposition) {
        this.columnposition = columnposition;
    }

    /**
     * @param validValue the validValue to set
     */
    public final void setInValidValue(final boolean validValue) {
        this.inValidValue = validValue;
    }

    /**
     * @return the validValue
     */
    public final boolean isInValidValue() {
        return inValidValue;
    }

}
