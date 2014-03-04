package com.ews.enterprise.integration.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChunkAndResponseWrapper implements Serializable {

	private static final long serialVersionUID = 2304101013189960207L;
	private List<Chunk> listChunk = new ArrayList<Chunk>();
    private Chunk singleChunk;

    public ChunkAndResponseWrapper(final List<Chunk> listChunk) {
        this.listChunk = listChunk;
    }

    /**
     * @return the listChunk
     */
    public final List<Chunk> getListChunk() {
        return listChunk;
    }

    /**
     * @param listChunk the listChunk to set
     */
    public final void setListChunk(final List<Chunk> listChunk) {
        this.listChunk = listChunk;
    }

    public final Chunk getSingleChunk() {
        return singleChunk;
    }

    public final void setSingleChunk(final Chunk singleChunk) {
        this.singleChunk = singleChunk;
    }
}
