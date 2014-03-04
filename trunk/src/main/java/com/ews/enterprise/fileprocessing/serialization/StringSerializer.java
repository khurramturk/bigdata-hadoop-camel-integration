package com.ews.enterprise.fileprocessing.serialization;

import java.util.List;

import com.ews.enterprise.fileprocessing.ColumnData;
/**
 * @author rana.ijaz
 */
public class StringSerializer implements TypeSerializer<String> {

	private static final long serialVersionUID = -4924693027337357144L;

	public final void serialize(final List<ColumnData> columns, final int index,
        final Object value) throws TypeSerializerException {
        if (null != value) {
            columns.add(index, new ColumnData(((String) value).trim(), this.getJavaType()));
        } else {
            columns.add(index, new ColumnData(null, this.getJavaType()));
        }
    }

    public final String deserialize(final String[] columns, final int index) throws TypeSerializerException {
        try {
            String column = columns[index];
            if (null != column) {
                return column.trim();
            } else {
                return column;
            }
        } catch (Exception ex) {
            throw new TypeSerializerException("Import Error: " + ex.getMessage());
        }
    }

    public final String getJavaType() throws TypeSerializerException {
        return String.class.getName();
    }
}
