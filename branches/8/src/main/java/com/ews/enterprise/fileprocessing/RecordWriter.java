package com.ews.enterprise.fileprocessing;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface RecordWriter {

    void write(final List<ColumnData> columns) throws IOException, ParseException;

    void close() throws IOException;

}
