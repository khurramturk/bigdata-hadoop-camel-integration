package com.ews.enterprise.fileprocessing;

import java.io.Serializable;
import java.util.List;

public class RowTemplate implements Serializable {

	private static final long serialVersionUID = -1322329021787003281L;
	private final List<ColumnTemplate> columnTemplates;
    private final Predicate<String[]> rowMatcher;
    private int rowCount = 0;

    public final int getRowCount() {
        return rowCount;
    }

    public final void setRowCount(final int rowCount) {
        this.rowCount = rowCount;
    }

    public RowTemplate(final List<ColumnTemplate> columnTemplates,
        final Predicate<String[]> rowMatcher) {
        this.columnTemplates = columnTemplates;
        this.rowMatcher = rowMatcher;
    }

    public final List<ColumnTemplate> getColumnTemplates() {
        return columnTemplates;
    }

    public final Predicate<String[]> getRowMatcher() {
        return rowMatcher;
    }

}
