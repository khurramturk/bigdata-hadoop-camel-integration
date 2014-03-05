package com.ews.enterprise.fileprocessing;

public class ColumnData {

    private String data;
    private String javaType;

    public ColumnData(final String strdata, final String type) {
        this.data = strdata;
        this.javaType = type;
    }

    /**
     * @return the data
     */
    public final String getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public final void setData(final String data) {
        this.data = data;
    }

    /**
     * @return the javaType
     */
    public final String getJavaType() {
        return javaType;
    }

    /**
     * @param javaType
     *            the javaType to set
     */
    public final void setJavaType(final String javaType) {
        this.javaType = javaType;
    }
}
