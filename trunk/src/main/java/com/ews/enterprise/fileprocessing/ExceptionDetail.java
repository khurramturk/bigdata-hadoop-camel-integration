package com.ews.enterprise.fileprocessing;

public class ExceptionDetail extends Exception {

    private static final long serialVersionUID = 7948076902794957514L;
    private String status;
    private int rowPosition;
    private String canonicalField;
    private String externalFileId;
    private String errorMsg;

    public final String getStatus() {
        return status;
    }

    public final int getRowPosition() {
        return rowPosition;
    }

    public final String getCanonicalField() {
        return canonicalField;
    }

    public final String getExternalFileId() {
        return externalFileId;
    }

    public final String getErrorMsg() {
        return errorMsg;
    }

    public final void setStatus(final String status) {
        this.status = status;
    }

    public final void setRowPosition(final int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public final void setCanonicalField(final String canonicalField) {
        this.canonicalField = canonicalField;
    }

    public final void setExternalFileId(final String externalFileId) {
        this.externalFileId = externalFileId;
    }

    public final void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
