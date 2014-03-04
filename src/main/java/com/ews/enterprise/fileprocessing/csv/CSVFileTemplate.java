package com.ews.enterprise.fileprocessing.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.ews.enterprise.fileprocessing.AbstractFileTemplate;
import com.ews.enterprise.fileprocessing.FileTemplate;
import com.ews.enterprise.fileprocessing.RecordReader;
import com.ews.enterprise.fileprocessing.RecordWriter;
import com.ews.enterprise.fileprocessing.RowTemplate;

public class CSVFileTemplate extends AbstractFileTemplate {

	private static final long serialVersionUID = 5680769373226872373L;
	// private final Charset charset;
    private final String encoding;
    private final char quoteCharacter;

    public CSVFileTemplate(final List<RowTemplate> rowTemplates,
        final String encoding,
        final char quoteCharacter) {
        super(rowTemplates);
        this.encoding = encoding;
        this.quoteCharacter = quoteCharacter;
    }

    public final RecordReader newRecordReader(final InputStream in) throws IOException {
        try {
            return new CSVFileReader(new InputStreamReader(in, encoding), quoteCharacter);
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e.getMessage());
        }

    }

    public final RecordWriter newRecordWriter(final OutputStream out) throws IOException {
        try {
            return new CSVFileWriter(new OutputStreamWriter(out, encoding));
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e.getMessage());
        }

    }

    public FileTemplate getNormalizedFileTemplate() {
        // TODO Auto-generated method stub
        return new CSVFileTemplate(getNormalizedRowTemplates(), encoding, quoteCharacter);
    }

}
