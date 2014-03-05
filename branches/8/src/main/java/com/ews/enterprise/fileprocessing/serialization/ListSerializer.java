package com.ews.enterprise.fileprocessing.serialization;

import java.util.LinkedList;
import java.util.List;

import com.ews.enterprise.fileprocessing.ColumnData;

/**
 * @author rana.ijaz
 */
public class ListSerializer<T> implements TypeSerializer<List<T>> {

	private static final long serialVersionUID = -852862978122467407L;
	private final TypeSerializer<T> elementSerializer;

    public ListSerializer(final TypeSerializer<T> elementSerializer) {
        this.elementSerializer = elementSerializer;
    }

    public final void serialize(final List<ColumnData> columns, final int index,
        final Object value) throws TypeSerializerException {

        // Iterator<T> iter = ((List<T>) value).iterator();
        // for (int i = position.getStart(); i < position.getEnd(); i++) {
        // elementSerializer.serialize(columns, new Position(i), iter.next());
        // }
    }

    public final List<T> deserialize(final String[] columns, final int index) throws TypeSerializerException {

        List<T> list = new LinkedList<T>();

        // for (int i = position.getStart(); i < position.getEnd(); i++) {
        // list.add(elementSerializer.deserialize(columns, new Position(i)));
        // }

        return list;
    }

    public final String getJavaType() throws TypeSerializerException {
        return List.class.getName();
    }
}
