package com.ews.enterprise.fileprocessing;

import java.io.Serializable;

public class Position implements Serializable {

	private static final long serialVersionUID = 3977716945924867345L;
	private final Integer start;
    private final Integer length;

    private final Integer columnPosition;

    public Position(final Integer start, final Integer length, final Integer columnPosition) {
        super();
        this.start = start;
        this.length = length;
        this.columnPosition = columnPosition;
    }

    public Position(final Integer columnPosition) {
    	super();
    	this.start = null;
    	this.length = null;
    	this.columnPosition = columnPosition;
    }

    public final Integer getStart() {
        return start;
    }

    public final Integer getLength() {
        return length;
    }

	public Integer getColumnPosition() {
		return columnPosition;
	}

}
