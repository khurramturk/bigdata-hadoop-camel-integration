package com.ews.enterprise.fileprocessing.serialization;

import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;


import com.ews.enterprise.fileprocessing.ColumnData;
/**
 * @author rana.ijaz
 */
public class MapSerializer<K, V> implements TypeSerializer<Map<K, V>> {

	private static final long serialVersionUID = 8438486579648858142L;
	private final TypeSerializer<K> keySerializer;
    private final TypeSerializer<V> valueSerializer;

    public MapSerializer(final TypeSerializer<K> keySerializer,
        final TypeSerializer<V> valueSerializer) {
        super();
        this.keySerializer = keySerializer;
        this.valueSerializer = valueSerializer;
    }

    public final void serialize(final List<ColumnData> columns, final int index,
        final Object value) throws TypeSerializerException {

        // Iterator<Entry<K, V>> iter = ((Map<K, V>)
        // value).entrySet().iterator();
        // for (int i = position.getStart(); i < position.getEnd(); i += 2) {
        // Entry<K, V> entry = iter.next();
        // keySerializer.serialize(columns, new Position(i), entry.getKey());
        // valueSerializer.serialize(columns, new Position(i + 1),
        // entry.getValue());
        // }
    }

    public final Map<K, V> deserialize(final String[] columns, final int index) throws TypeSerializerException {

        Map<K, V> map = new HashMap<K, V>();
        // for (int i = position.getStart(); i < position.getEnd(); i += 2) {
        // map.put(keySerializer.deserialize(columns, new Position(i)),
        // valueSerializer.deserialize(columns, new Position(i + 1)));
        // }
        return map;
    }

    public final String getJavaType() throws TypeSerializerException {
        return Map.class.getName();
    }

}
