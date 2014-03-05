package com.ews.enterprise.fileprocessing;

public class ColumnEqualsPredicate implements Predicate<String[]> {

	private static final long serialVersionUID = 3455238898016387662L;
	private final int columnIndex;
	private final String columnValue;

	public ColumnEqualsPredicate(final int columnIndex, final String columnValue) {
		super();
		this.columnIndex = columnIndex;
		this.columnValue = columnValue;
	}

	public final boolean apply(final String[] rowData) {
		if (rowData == null) {
			throw new NullPointerException();
		}
		return rowData[columnIndex].equals(columnValue);
	}

}
