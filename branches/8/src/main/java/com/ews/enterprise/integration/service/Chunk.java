package com.ews.enterprise.integration.service;

import java.io.Serializable;
import java.util.List;

import com.ews.enterprise.fileprocessing.Record;
import com.ews.enterprise.integration.FileProcessingContext;

public class Chunk implements Serializable {

    private static final long serialVersionUID = 2881734789527488187L;
    private final List<Record> records;
    private final FileProcessingContext context;

    public Chunk(final List<Record> records, final FileProcessingContext context) {
        this.records = records;
        this.context = context;
    }

    public final List<Record> getRecords() {
        return records;
    }

    public final FileProcessingContext getContext() {
        return context;
    }

    public final void add(final Chunk chunk) {
        for (Record r : chunk.getRecords()) {
            this.records.add(r);
        }
    }

}
