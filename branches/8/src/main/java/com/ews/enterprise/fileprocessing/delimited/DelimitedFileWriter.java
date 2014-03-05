package com.ews.enterprise.fileprocessing.delimited;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.ews.enterprise.fileprocessing.ColumnData;
import com.ews.enterprise.fileprocessing.RecordWriter;

public class DelimitedFileWriter implements RecordWriter {

    private final BufferedWriter writer;
    private final DelimitedFileTemplate template;

    public DelimitedFileWriter(final Writer writer, final DelimitedFileTemplate template) {
        super();
        this.template = template;
        if (!(writer instanceof BufferedWriter)) {
            this.writer = new BufferedWriter(writer);
        } else {
            this.writer = (BufferedWriter) writer;
        }
    }

    public final void write(final List<ColumnData> columns) throws IOException {
        String data = null;
        int length = columns.size();
        int writeIndex = 0;
        for (ColumnData cd : columns) {
            data = cd.getData();
            if (data != null) {
                writer.write(data);
            }
            writeIndex++;
            if (writeIndex < length) {
                writer.write("\\t".equalsIgnoreCase(template.getDelimiter()) ? String.valueOf('\t') : template
                    .getDelimiter());

            }
        }
        writer.newLine();
    }

    public final void close() throws IOException {
        writer.close();
    }
}
