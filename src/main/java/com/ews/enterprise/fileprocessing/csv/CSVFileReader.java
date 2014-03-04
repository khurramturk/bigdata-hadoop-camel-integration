package com.ews.enterprise.fileprocessing.csv;

import java.io.IOException;
import java.io.Reader;

import au.com.bytecode.opencsv.CSVReader;

import com.ews.enterprise.fileprocessing.RecordReader;

public class CSVFileReader implements RecordReader {

    private final CSVReader reader;

    public CSVFileReader(final Reader r, final char quoteCharacter) {
        reader = new CSVReader(r, ',', quoteCharacter);
    }

    public final String[] read() throws IOException {
        String[] row = null;
        row = reader.readNext();
        return row;
    }

    public final void close() throws IOException {
        reader.close();
    }

}
