package com.ews.enterprise.fileprocessing;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileTemplate implements FileTemplate {

    private static final long serialVersionUID = -6194795252193001802L;
    private static final String STATE = "STATE";
    private static final String CITY = "CITY";
    private static final String ZIP = "ZIP";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String MIDDLE_NAME = "MIDDLE_NAME";
    private final List<RowTemplate> rowTemplates;

    private String exportFilePath;
    private String templateId;

    public AbstractFileTemplate(final List<RowTemplate> rowTemplates) {
        super();
        this.rowTemplates = rowTemplates;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.envisagesystems.fileprocessing.FileTemplate#getRowTemplates()
     */
    public final List<RowTemplate> getRowTemplates() {
        return rowTemplates;
    }

    public final String getExportFilePath() {
        return exportFilePath;
    }

    public final void setExportFilePath(final String exportFilePath) {
        this.exportFilePath = exportFilePath;
    }

    public final String getTemplateId() {
        return templateId;
    }

    public final void setTemplateId(final String templateId) {
        this.templateId = templateId;
    }

    public final List<RowTemplate> getNormalizedRowTemplates() {
        List<RowTemplate> rTemplates = new ArrayList<RowTemplate>();
        for (RowTemplate row : rowTemplates) {
            if (null != row) {
                List<ColumnTemplate> cols = new ArrayList<ColumnTemplate>();
                for (ColumnTemplate col : row.getColumnTemplates()) {
                    if (col.getField().getFieldName().equalsIgnoreCase(CombinedCanonicalFields.CITY_STATE.toString())) {
                        String[] fields = {CITY, STATE};
                        cols.addAll(createColumnTemplates(col, fields));
                    } else if (col.getField().getFieldName()
                        .equalsIgnoreCase(CombinedCanonicalFields.CITY_STATE_ZIP.toString())) {
                        String[] fields = {CITY, STATE, ZIP};
                        cols.addAll(createColumnTemplates(col, fields));
                    } else if (col.getField().getFieldName()
                        .equalsIgnoreCase(CombinedCanonicalFields.FIRST_LAST_NAME.toString())) {
                        String[] fields = {FIRST_NAME, LAST_NAME};
                        cols.addAll(createColumnTemplates(col, fields));
                    } else if (col.getField().getFieldName()
                        .equalsIgnoreCase(CombinedCanonicalFields.LAST_FIRST_NAME.toString())) {
                        String[] fields = {LAST_NAME, FIRST_NAME};
                        cols.addAll(createColumnTemplates(col, fields));
                    } else if (col.getField().getFieldName()
                        .equalsIgnoreCase(CombinedCanonicalFields.FULL_NAME.toString())) {
                        String[] fields = {FIRST_NAME, MIDDLE_NAME, LAST_NAME};
                        cols.addAll(createColumnTemplates(col, fields));
                    } else {
                        cols.add(col);
                    }
                }
                RowTemplate rowTemplate = new RowTemplate(cols, null);
                rTemplates.add(rowTemplate);
            }
        }
        return rTemplates;

    }

    private List<ColumnTemplate> createColumnTemplates(ColumnTemplate col, String[] fields) {
        List<ColumnTemplate> cols = new ArrayList<ColumnTemplate>();
        for (String field : fields) {
            Field fieldValue = new Field(field, field);
            ColumnTemplate column = new ColumnTemplate(col.getPosition(), fieldValue, col.getTypeSerializer());
            cols.add(column);
        }
        return cols;

    }
}
