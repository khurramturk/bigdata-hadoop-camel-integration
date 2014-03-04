package com.ews.enterprise.fileprocessing;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.poifs.eventfilesystem.POIFSReader;

import com.ews.enterprise.fileprocessing.serialization.DoubleSerializer;
import com.ews.enterprise.fileprocessing.serialization.StringSerializer;
import com.ews.enterprise.fileprocessing.serialization.TypeSerializerException;

public class DefaultFileImporter implements FileImporter {

    private static final String STATE = "STATE";
    private static final String CITY = "CITY";
    private static final String ZIP = "ZIP";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String MIDDLE_NAME = "MIDDLE_NAME";

    private final FileTemplate template;

    public DefaultFileImporter(final FileTemplate template) {
        super();
        this.template = template;
    }

    protected final FileTemplate getTemplate() {
        return template;
    }

    public final void parse(final InputStream in, final RecordHandler handler) throws FileImportException, IOException {
        // if (template instanceof ExcelFileTemplate) {
        // if (detectMacros(in)) {
        // ExceptionDetail exceptionDetail = new ExceptionDetail();
        // exceptionDetail.setStatus("Error");
        // exceptionDetail.setRowPosition(0);
        // exceptionDetail.setErrorMsg("Uploaded ExcelFile contains Macros. Macros are not supported");
        // handler.error(exceptionDetail);
        // Record record = new Record();
        // handler.handleRecord(record);
        // handler.eof();
        // return;
        // } else {
        // in.reset();
        // }
        // }
        RecordReader reader = template.newRecordReader(in);
        String[] fields = reader.read();
        int rowNumber = 0;
        while (fields != null) {
            rowNumber++;
            RowTemplate rowTemplate = template.getRowTemplates().get(0);
            // No matching row template
            if (rowTemplate == null) {
                ExceptionDetail exceptionDetail = new ExceptionDetail();
                exceptionDetail.setErrorMsg("No rows in the template");
                handler.error(exceptionDetail);
            } else {
                // Build structure
                Record record = buildRecord(rowTemplate, fields, rowNumber, handler);
                handler.handleRecord(record);
            }
            fields = reader.read();
        }
        handler.eof();
    }

    private Record buildRecord(final RowTemplate rowTemplate, final String[] fields, final int rowNumber,
        final RecordHandler handler) throws FileImportException {
        Record record = new Record();
        List<Column> columns = new ArrayList<Column>();
        // int colPosition = 0;
        int index = 0;
        for (ColumnTemplate colTemplate : rowTemplate.getColumnTemplates()) {
            Column column = new Column();
            String value = null;
            try {
                record.setRowpos(rowNumber);
                value = fields[index];
                columns.addAll(createColumns(colTemplate, fields, index));
                // column.setColumnposition(++colPosition);
            } catch (Exception ex) {
                column.setValue(value);
                column.setCanonicalName(colTemplate.getField().getFieldName());
                column.setColumnposition(index);
                columns.add(column);
                ExceptionDetail exceptionDetail = new ExceptionDetail();
                exceptionDetail.setCanonicalField(colTemplate.getField().getFieldName());
                exceptionDetail.setRowPosition(rowNumber);
                exceptionDetail.setStatus("Error");
                if (ex instanceof TypeSerializerException) {
                    exceptionDetail
                        .setErrorMsg(ex.getMessage()
                            + "Data type errors must be corrected and the file re-validated before"
                            + " rule validations are performed. Value is: " + value);

                } else {
                    exceptionDetail
                        .setErrorMsg("Import Error: Template selected does not match the input row."
                            + "Please correct the file and resubmit");
                }
                handler.error(exceptionDetail);
            }
            index++;
            // columns.add(column);
        }
        record.setColumns(columns);
        return record;
    }
//CHECKSTYLE:OFF
    private List<Column> createColumns(final ColumnTemplate colTemplate, final String[] fields, int index)
        throws TypeSerializerException {
//CHECKSTYLE:ON        
        Object obj = null;
        List<Column> columns = new ArrayList<Column>();
        String value = fields[index];
        if (value != null && !"".equalsIgnoreCase(value)) {
            obj = colTemplate.getTypeSerializer().deserialize(fields, index);
        } else {
            obj = getEmptyColumnValue(colTemplate);
        }
        String name = colTemplate.getField().getFieldName();
        if (name.equalsIgnoreCase(CombinedCanonicalFields.CITY_STATE.toString())) {
            String[] values = {"", ""};
            String[] tokens = ((String) obj).split(",");
            int length = tokens.length;
            for (int i = 0; i < length; i++) {
                values[i] = tokens[i];
            }
            String[] canonicalFields = {CITY, STATE};
            columns = createColumn(canonicalFields, values, ++index);

        } else if (name.equalsIgnoreCase(CombinedCanonicalFields.CITY_STATE_ZIP.toString())) {
            String[] canonicalFields = {CITY, STATE, ZIP};
            String zip = checkZip(value);
            String cityState = value.substring(0, value.lastIndexOf(zip));
            String[] tokens = cityState.split(",");
            String[] values = {"", "", ""};
            int length = tokens.length;
            for (int i = 0; i < length; i++) {
                if (null != tokens[i]) {
                    values[i] = tokens[i].trim();
                }
            }
            values[2] = zip;
            columns = createColumn(canonicalFields, values, ++index);
        } else if (name.equalsIgnoreCase(CombinedCanonicalFields.FIRST_LAST_NAME.toString())) {
            String[] canonicalFields = {FIRST_NAME, LAST_NAME};
            String[] values = {"", ""};
            if (null != obj && !(((String) obj).length()==0)) {
                String[] tokens = ((String) obj).split(" ");
                values[0] = tokens[0];
                int size = tokens.length;
                if (size == 2) {
                    values[1] = tokens[size - 1];
                } else if (size > 2) {
                    for (int i = 1; i <= size - 1; i++) {
                        values[1] = values[1].concat(" ").concat(tokens[i]);
                    }
                }
            }
            columns = createColumn(canonicalFields, values, ++index);
        } else if (name.equalsIgnoreCase(CombinedCanonicalFields.LAST_FIRST_NAME.toString())) {
            String[] canonicalFields = {LAST_NAME, FIRST_NAME};
            String[] values = {"", ""};
            if (null != obj && !(((String) obj).length()==0)) {
                String[] tokens = ((String) obj).split(" ");
                values[0] = tokens[0];
                int size = tokens.length;
                if (size == 2) {
                    values[1] = tokens[size - 1];
                } else if (size > 2) {
                    for (int i = 1; i <= size - 1; i++) {
                        values[1] = values[1].concat(" ").concat(tokens[i]);
                    }
                }
            }
            columns = createColumn(canonicalFields, values, ++index);
        } else if (name.equalsIgnoreCase(CombinedCanonicalFields.FULL_NAME.toString())) {
            String[] canonicalFields = {FIRST_NAME, MIDDLE_NAME, LAST_NAME};
            String[] values = {"", "", ""};
            if (null != obj && !(((String) obj).length()==0)) {
                String[] tokens = ((String) obj).split(" ");
                values[0] = tokens[0];
                int size = tokens.length;
                if (size > 1) {
                    values[2] = tokens[size - 1];
                }
                if (size > 2) {
                    for (int i = 1; i <= size - 2; i++) {
                        values[i] = tokens[i].concat(" ");
                    }
                }
            }
            columns = createColumn(canonicalFields, values, ++index);
        } else {
            Column column = new Column();
            column.setCanonicalName(name);
            column.setValue(obj);
            column.setColumnposition(++index);
            columns.add(column);
        }
        return columns;
    }

    private List<Column> createColumn(String[] canonicalNames, String[] values, int colpos) {
        List<Column> columns = new ArrayList<Column>();
        for (int i = 0; i < canonicalNames.length; i++) {
            Column column = new Column();
            column.setCanonicalName(canonicalNames[i]);
            column.setValue(values[i].trim());
            column.setColumnposition(colpos);
            columns.add(column);
        }
        return columns;

    }

    private String checkZip(String address) throws TypeSerializerException {
        String zip = address.substring(address.lastIndexOf(" ") + 1);
        if (zip.length() == 3) {
            zip = address.substring(address.lastIndexOf(" ") - 3);
        }
        return zip;
    }

    private boolean detectMacros(InputStream in) throws IOException {
        POIFSReader r = new POIFSReader();
        MacroListener ml = new MacroListener();
        r.registerListener(ml);
        if (!in.markSupported()) {
            in = new PushbackInputStream(in);
        } else {
            in.mark(0);
        }
        r.read(in);
        return ml.isMacroDetected();
    }

    private Object getEmptyColumnValue(ColumnTemplate colTemplate) {
        Object obj = null;
        if (colTemplate.getTypeSerializer() instanceof StringSerializer) {
            obj = "";
        }
        else if (colTemplate.getTypeSerializer() instanceof DoubleSerializer) {
            obj = BigDecimal.ZERO;
        }
        return obj;
    }
}
