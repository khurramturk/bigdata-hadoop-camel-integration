package com.ews.enterprise.fileprocessing;

import java.io.Serializable;
import java.util.List;

public class Record implements Serializable {

	private static final long serialVersionUID = -5035396421535310147L;
	// private final RowTemplate rowTemplate;
    private List<Column> columns;
    private int rowpos;

    // public Record() {
    //
    // }

    // public Record(final RowTemplate rowTemplate) {
    // this.rowTemplate = rowTemplate;
    // }

    // public final RowTemplate getRowTemplate() {
    // return rowTemplate;
    // }

    public final int getRowpos() {
        return rowpos;
    }

    public final void setRowpos(final int rowpos) {
        this.rowpos = rowpos;
    }

    public final List<Column> getColumns() {
        return columns;
    }

    public final void setColumns(final List<Column> columns) {
        this.columns = columns;
    }

}
