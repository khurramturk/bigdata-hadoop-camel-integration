package com.ews.enterprise.datacollection.orm.mongo;

import java.util.Date;

public class MongoCamelDcr {

    private String fromEndpoint;

    public final String getFromEndpoint() {
        return fromEndpoint;
    }

    public final void setFromEndpoint(final String fromEndpoint) {
        this.fromEndpoint = fromEndpoint;
    }

    private String toNode;

    public final String getBotServer() {
        return toNode;
    }

    public final void setToNode(final String toNode) {
        this.toNode = toNode;
    }

    private String exchangeId;

    public final String getExchangeId() {
        return exchangeId;
    }

    public final void setExchangeId(final String exchangeId) {
        this.exchangeId = exchangeId;
    }

    private Date ts;

    public final Date getTs() {
        return ts;
    }

    public final void setTs(final Date ts) {
        this.ts = ts;
    }

    private String fileId;

    public final String getFileId() {
        return fileId;
    }

    public final void setFileId(final String fileId) {
        this.fileId = fileId;
    }

    private int duration;

    public final int getDuration() {
        return duration;
    }

    public final void setDuration(final int duration) {
        this.duration = duration;
    }
}
