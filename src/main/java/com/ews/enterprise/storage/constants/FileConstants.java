package com.ews.enterprise.storage.constants;

public class FileConstants {
	private FileConstants() {
    }

    public static final String EXTERNAL_FILE_ID = "EXTERNAL_FILE_ID";
    public static final String RECORD_POSITION = "RECORD_POSITION";
    public static final String USER_ID = "USER_ID";
    public static final String VERSION_NUMBER = "VERSION_NUMBER";
    public static final String DATA = "DATA";
    public static final long CURRENT_VERSION_NUMBER = 0;
    public static final String RECORD_DATA = "RECORD_DATA";
    public static final String RECORD_ID = "RECORD_ID";

    public static final String COLUMN_DATA = "COLUMN_DATA";
    public static final String COLUMN_CANONICAL_NAME = "COLUMN_CANONICAL_NAME";
    public static final String COLUMN_POSITION = "COLUMN_POSITION";
    public static final String COLUMN_VALUE = "COLUMN_VALUE";
    public static final String CANONICAL_NAMES_LIST = "CANONICAL_NAMES_LIST";

    public static final String FILE_NAME = "FILE_NAME";
    public static final String METADATA_APPENDER = "metadata.";

    public static final String ROW_TEMPLATE_ID = "ROW_TEMPLATE_ID";
    public static final String IMPORT_TEMPLATE_ID = "IMPORT_TEMPLATE_ID";
    public static final String EXPORT_TEMPLATE_ID = "EXPORT_TEMPLATE_ID";
    public static final int INCREMENT_BY_ONE = 1;
    public static final String APPENDER = ".";

    public static final String VALIDATION_DATA = "VALIDATION_DATA";
    public static final String VALIDATION_STATUS = "VALIDATION_STATUS";
    public static final String VALIDATION_RULE = "VALIDATION_RULE";
    public static final String VALIDATION_ACTION_TYPE = "VALIDATION_ACTION_TYPE";
    public static final String VALIDATION_DESCRIPTION = "VALIDATION_DESCRIPTION";

    // All Collections for Mongo
    public static final String FILE_COLLECTION = "FILE_COLLECTION";
    public static final String VALIDATION_RESULT_COLLECTION = "VALIDATION_RESULT_COLLECTION";
}
