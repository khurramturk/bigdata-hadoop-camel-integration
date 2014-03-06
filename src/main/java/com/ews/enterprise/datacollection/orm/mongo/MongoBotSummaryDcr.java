package com.ews.enterprise.datacollection.orm.mongo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MongoBotSummaryDcr {
    // what bot was processing and where
    private String botName;
    private String botServer;
    // how long did the bot task take
    private Date tsBegin;
    private Date tsEnd;
    // what was the bot doing
    private String task;
    // what type of file
    private String fileType;
    // what was the file
    private UUID fileId;

    public MongoBotSummaryDcr() {
        Calendar cal = Calendar.getInstance();
        botName = "unknown";
        botServer = "unknown";
        tsBegin = new Date(cal.getTimeInMillis());
        tsEnd = new Date(cal.getTimeInMillis());
        task = "unknown";
        fileType = "unknown";
        fileId = null;
    }

    public final String getBotName() {
        return botName;
    }

    public final void setBotName(final String botName) {
        this.botName = botName;
    }

    public final String getBotServer() {
        return botServer;
    }

    public final void setBotServer(final String botServer) {
        this.botServer = botServer;
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

    public final String getTask() {
        return task;
    }

    public final void setTask(final String task) {
        this.task = task;
    }

    public final String getFileType() {
        return fileType;
    }

    public final void setFileType(final String fileType) {
        this.fileType = fileType;
    }

    public final UUID getFileId() {
        return fileId;
    }

    public final void setFileId(final UUID fileId) {
        this.fileId = fileId;
    }
}