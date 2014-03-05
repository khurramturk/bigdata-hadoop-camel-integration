package com.ews.enterprise.fileprocessing.delimited;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Pattern;

import com.ews.enterprise.fileprocessing.AbstractFileTemplate;
import com.ews.enterprise.fileprocessing.FileTemplate;
import com.ews.enterprise.fileprocessing.RecordReader;
import com.ews.enterprise.fileprocessing.RecordWriter;
import com.ews.enterprise.fileprocessing.RowTemplate;

public class DelimitedFileTemplate extends AbstractFileTemplate {

	private static final long serialVersionUID = -5738197858333325536L;
	private final String delimiter;
    private final Pattern delimiterPattern;
    private final String encoding;
    private final char quoteChar;
    private static final String PIPE_DELIMITER = "|";
    private static final String TAB_DELIMETER = "\t";
    private static final String COMMA_DELIMETER = ",";
    private static final String QUOTATION_DELIMETER = "\"";
    private static final String SPACE_DELIMETER = "";
    private static final String SEMICOLON_DELIMETER = ";";

    public DelimitedFileTemplate(final String delimiter,
        final List<RowTemplate> rowTemplates,
        final String encoding, final char quoteChar) {
        super(rowTemplates);
        this.delimiter = delimiter;
        this.delimiterPattern = Pattern.compile(translateDelimiterToRegex(delimiter));
        this.encoding = encoding;
        this.quoteChar = quoteChar;
    }

    public final String getDelimiter() {
        return delimiter;
    }

    public final Pattern getDelimiterPattern() {
        return delimiterPattern;
    }

    private String translateDelimiterToRegex(final String s) {
        String translated = s;
        // Replace "|" w "\|"
        if (s.equalsIgnoreCase(PIPE_DELIMITER)) {
            translated = translated.replaceAll("\\|", "\\\\|");
        } else if (s.equalsIgnoreCase(TAB_DELIMETER)) {
            translated = translated.replaceAll("\\\\t", "\\\\t");
        } else if (s.equalsIgnoreCase(SPACE_DELIMETER)) {
            translated = translated.replaceAll("\\s", "\\\\\\s*");
        }
        return translated;
    }

    public final RecordReader newRecordReader(final InputStream in) throws IOException {
        try {
            return new DelimitedFileReader(new InputStreamReader(in, encoding), this);
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e.getMessage());
        }
    }

    public final RecordWriter newRecordWriter(final OutputStream out) throws IOException {
        try {
            return new DelimitedFileWriter(new OutputStreamWriter(out, encoding), this);
        } catch (UnsupportedEncodingException e) {
            throw new IOException(e.getMessage());
        }
    }

    public final FileTemplate getNormalizedFileTemplate() {
        return new DelimitedFileTemplate(delimiter, getNormalizedRowTemplates(), encoding, quoteChar);
    }

    public final char getQuoteChar() {
        return quoteChar;
    }

}
