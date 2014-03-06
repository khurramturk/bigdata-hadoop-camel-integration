package com.ews.enterprise.datacollection.orm.mongo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MongoBotDetailDcr {
    // how long did the bot task take
    private Date tsBegin;
    private Date tsEnd;
    // what was the file
    private UUID fileId;
    // what validator class was running
    private String validator;
    // what row num
    private int rowNum;
    // what column num
    private int colNum;

    public MongoBotDetailDcr() {
        Calendar cal = Calendar.getInstance();
        tsBegin = new Date(cal.getTimeInMillis());
        tsEnd = new Date(cal.getTimeInMillis());
        fileId = null;
        validator = "unknown";
        rowNum = -1;
        colNum = -1;
    }

    public final Date getTsBegin() {
        return tsBegin;
    }

    public final void setTsBegin(final Date tsBegin) {
        this.tsBegin = tsBegin;
    }

    public final Date getTsEnd() {
        return tsEnd;
    }

    public final void setTsEnd(final Date tsEnd) {
        this.tsEnd = tsEnd;
    }

    public final UUID getFileId() {
        return fileId;
    }

    public final void setFileId(final UUID fileId) {
        this.fileId = fileId;
    }

    public final String getValidator() {
        return validator;
    }

    public final void setValidator(final String validator) {
        this.validator = validator;
    }

    public final int getRowNum() {
        return rowNum;
    }

    public final void setRowNum(final int rowNum) {
        this.rowNum = rowNum;
    }

    public final int getColNum() {
        return colNum;
    }

    public final void setColNum(final int colNum) {
        this.colNum = colNum;
    }
}
