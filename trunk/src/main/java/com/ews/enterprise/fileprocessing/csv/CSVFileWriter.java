package com.ews.enterprise.fileprocessing.csv;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import com.ews.enterprise.fileprocessing.ColumnData;
import com.ews.enterprise.fileprocessing.RecordWriter;

public class CSVFileWriter implements RecordWriter {

    private CSVWriter writer;

    public CSVFileWriter(final Writer w) {
        this.writer = new CSVWriter(w, ',', '"');
    }

    public final void write(final List<ColumnData> columns) throws IOException {

        List<String[]> lines = new ArrayList<String[]>();
        String[] arrColumns = new String[columns.size()];
        int i = 0;
        for (ColumnData cd : columns) {
            arrColumns[i] = cd.getData();
            i++;
        }

        lines.add(arrColumns);
        // writer.writeNext(columns);
        writer.writeAll(lines);

    }

    public final void close() throws IOException {
        writer.close();
    }

}
