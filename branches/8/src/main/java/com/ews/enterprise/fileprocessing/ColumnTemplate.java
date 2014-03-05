package com.ews.enterprise.fileprocessing;

import java.io.Serializable;

import com.ews.enterprise.fileprocessing.serialization.TypeSerializer;

public class ColumnTemplate implements Serializable {

	private static final long serialVersionUID = -5973469014039358588L;
	private Position position;
	private final Field field;
	private final TypeSerializer<? extends Object> typeSerializer;
	private boolean identifiableColumn;
	private boolean calculateTotals;

	public ColumnTemplate(final Position position, final Field field, final TypeSerializer<? extends Object> typeSerializer) {

		this.position = position;
		this.field = field;
		this.typeSerializer = typeSerializer;
	}

	public final Position getPosition() {
		return position;
	}

	public final void setPosition(final Position p) {
		this.position = p;
	}

	public final Field getField() {
		return field;
	}

	public final TypeSerializer<? extends Object> getTypeSerializer() {
		return typeSerializer;
	}

	public boolean isIdentifiableColumn() {
		return identifiableColumn;
	}

	public void setIdentifiableColumn(final boolean identifiableColumn) {
		this.identifiableColumn = identifiableColumn;
	}

	public boolean isCalculateTotals() {
		return calculateTotals;
	}

	public void setCalculateTotals(final boolean calculateTotals) {
		this.calculateTotals = calculateTotals;
	}

}
