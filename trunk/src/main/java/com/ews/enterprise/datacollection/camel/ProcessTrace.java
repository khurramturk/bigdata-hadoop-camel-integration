package com.ews.enterprise.datacollection.camel;

import java.util.Date;

public class ProcessTrace {
    private String fileID;
    private String fromEndpoint;
    private String toNode;
    private String exchangeId;
    private Date ts;

    public final String getFileID() {
        return fileID;
    }

    public final void setFileID(final String fileID) {
        this.fileID = fileID;
    }

    public final String getFromEndpoint() {
        return fromEndpoint;
    }

    public final void setFromEndpoint(final String fromEndpoint) {
        this.fromEndpoint = fromEndpoint;
    }

    public final String getToNode() {
        return toNode;
    }

    public final void setToNode(final String toNode) {
        this.toNode = toNode;
    }

    public final String getExchangeId() {
        return exchangeId;
    }

    public final void setExchangeId(final String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public final Date getTs() {
        return ts;
    }

    public final void setTs(final Date ts) {
        this.ts = ts;
    }
}
