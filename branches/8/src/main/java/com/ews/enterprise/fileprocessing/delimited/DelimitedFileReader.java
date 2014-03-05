package com.ews.enterprise.fileprocessing.delimited;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ews.enterprise.fileprocessing.RecordReader;

public class DelimitedFileReader implements RecordReader {

	private final BufferedReader reader;
	private final DelimitedFileTemplate template;

	public DelimitedFileReader(final Reader reader,
			final DelimitedFileTemplate template) {
		this.template = template;

		if (!(reader instanceof BufferedReader)) {
			this.reader = new BufferedReader(reader);
		} else {
			this.reader = (BufferedReader) reader;
		}
	}

	public final String[] read() throws IOException {
		String[] result = null;
		String line;

		do {
			line = reader.readLine();
		} while ("".trim().equals(line)); // Skip blank lines

		if (line == null) { // EOF
			return null;
		} else {

			if (line.indexOf(String.valueOf(template.getQuoteChar())) >= 0) {
				result = parseDelimitedLineWithEscapeChars(
						template.getDelimiter(),
						String.valueOf(template.getQuoteChar()), line);
			} else {
				result = template.getDelimiterPattern().split(line, -1);
			}
		}
		return result;
	}

	public final void close() throws IOException {
		reader.close();
	}

	public static String[] parseDelimitedLineWithEscapeChars(String delimiter,
			String quoteChar, String line) {
		List<String> matchList = new ArrayList<String>();

		
		delimiter = "\\" + delimiter;
		quoteChar = "\\" + quoteChar;
		Pattern pattern = Pattern.compile("[^" + delimiter + quoteChar
				+ "]+|" + quoteChar + "(.*?)" + quoteChar + "(" + delimiter
				+ "|$)");

		Matcher regexMatcher = pattern.matcher(line);

		while (regexMatcher.find()) {
			if (regexMatcher.group(1) != null) {
				matchList.add(regexMatcher.group(1));
			} else {
				matchList.add(regexMatcher.group());
			}
		}
		String[] tokens = (String[]) matchList.toArray(new String[matchList.size()]);

		return tokens;
	}
}
