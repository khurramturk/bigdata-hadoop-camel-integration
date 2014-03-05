package com.ews.enterprise.fileprocessing.serialization;

import java.math.BigInteger;
import java.util.List;

import com.ews.enterprise.fileprocessing.ColumnData;
/**
 * @author rana.ijaz
 */
public class IntegerSerializer implements TypeSerializer<BigInteger> {

    private static final long serialVersionUID = -8805188850822863254L;

    public final void serialize(final List<ColumnData> columns, final int index,
        final Object value) throws TypeSerializerException {
        // columns[position.getStart()] = value.toString();
        columns.add(index, new ColumnData(value.toString(), this.getJavaType()));
    }

    public final BigInteger deserialize(final String[] columns, final int index) throws TypeSerializerException {
        try {
            return new BigInteger(columns[index].trim());
        } catch (Exception ex) {
            throw new TypeSerializerException("Import Error: " + ex.getMessage());
        }
    }

    public final String getJavaType() throws TypeSerializerException {
        return BigInteger.class.getName();
    }

}
