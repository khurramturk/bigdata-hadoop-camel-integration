package com.ews.enterprise.fileprocessing.serialization;

import java.io.Serializable;
import java.util.List;

import com.ews.enterprise.fileprocessing.ColumnData;
/**
 * @author rana.ijaz
 */
public interface TypeSerializer<T> extends Serializable {

    void serialize(List<ColumnData> columns, int index, Object value) throws TypeSerializerException;

    T deserialize(String[] columns, int index) throws TypeSerializerException;

    String getJavaType() throws TypeSerializerException;

    Integer YEAR_LOW_LIMIT = 1900;
    Integer YEAR_HIGH_LIMIT = 2100;

}
