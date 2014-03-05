package com.ews.enterprise.integration.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.ews.enterprise.fileprocessing.Column;
import com.ews.enterprise.fileprocessing.ColumnTemplate;
import com.ews.enterprise.fileprocessing.Record;
import com.ews.enterprise.fileprocessing.serialization.TypeSerializerException;
import com.ews.enterprise.integration.FileProcessingContext;
import com.ews.enterprise.integration.dao.FileStorageDAO;
import com.ews.enterprise.storage.constants.FileConstants;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class FileStorageServiceImpl implements FileStorageService {
	private FileStorageDAO dao = null;

	@Inject
	public FileStorageServiceImpl(final FileStorageDAO dao) {
		this.dao = dao;
	}

	@Override
	public final void saveFileAsIs(final FileSubmission fileSubmission)
			throws Exception {
		if (fileSubmission != null) {
			final FileProcessingContext context = fileSubmission.getContext();
			if (context != null) {
				dao.saveFile(fileSubmission.getFileContents(),
						context.getFileName(), buildMetaData(context));
			}
		}
	}
	
	@Override
    public final void insertRecords(final List<Record> recordList,
        final FileProcessingContext context)
        throws Exception {
        final String externalFileID = context.getExternalFileID();
        final List<ColumnTemplate> columnTemplates = context.getImportTemplate().getRowTemplates().get(0)
            .getColumnTemplates();
        List<DBObject> list = new ArrayList<DBObject>();
        for (final Record record : recordList) {
            final BasicDBObject data = createDBObjectForRecord(record, externalFileID, columnTemplates,"SUBMITTED_BY");
            list.add(data);
        }
        dao.saveData(list, FileConstants.FILE_COLLECTION);
    }

	private BasicDBObject buildMetaData(final FileProcessingContext context) {
        final BasicDBObject metaData = new BasicDBObject();
        metaData.append("EXTERNAL_FILE_ID", context.getExternalFileID());
        metaData.append("FILE_NAME", context.getFileName());
        return metaData;
    }
	
	public final BasicDBObject createDBObjectForRecord(final Record record,
	        final String externalFileID,
	        final List<ColumnTemplate> columnTemplates,
	        final String editedByUserId) {

	        List<BasicDBObject> columnList = null;
	        final BasicDBObject data = new BasicDBObject();
	        data.append(FileConstants.EXTERNAL_FILE_ID, externalFileID);
	        data.append(FileConstants.VERSION_NUMBER, FileConstants.CURRENT_VERSION_NUMBER);
	        data.append(FileConstants.RECORD_POSITION, record.getRowpos());
	        // if (null != editedByUserId) {
	        data.append(FileConstants.USER_ID, editedByUserId);
	        // }
	        if (record.getColumns() != null) {
	            columnList = unpackColumnData(record.getColumns(), columnTemplates);
	        }
	        data.append(FileConstants.COLUMN_DATA, columnList);
	        return data;
	    }
	
	private List<BasicDBObject> unpackColumnData(final List<Column> columnList,
	        final List<ColumnTemplate> columnTemplates) {
	        final List<BasicDBObject> columnData = new ArrayList<BasicDBObject>();
	        for (final Column column : columnList) {
	            // TODO This logic should be changed. Performance Hit
	        outerloop:
	            for (final ColumnTemplate columnTemplate : columnTemplates) {
	                if (column.getCanonicalName().equals(columnTemplate.getField().getFieldName())) {
	                    String columnClass;
	                    try {
	                        columnClass = columnTemplate.getTypeSerializer().getJavaType();
	                    } catch (final TypeSerializerException e) {
	                        columnClass = String.class.getName();
	                    }

	                    final BasicDBObject detail = new BasicDBObject();
	                    detail.append(FileConstants.COLUMN_POSITION, column.getColumnposition());

	                    try {
	                        if (columnClass.equals(BigDecimal.class.getName())) {
	                            detail.append(FileConstants.COLUMN_VALUE, ((BigDecimal) column.getValue()).toPlainString());
	                        } else if (columnClass.equals(BigInteger.class.getName())) {
	                            detail.append(FileConstants.COLUMN_VALUE, ((BigInteger) column.getValue()).toString());
	                        } else {
	                            detail.append(FileConstants.COLUMN_VALUE, column.getValue());
	                        }
	                    } catch (final Exception e) {
	                        // This means an exception has been thrown. Done for
	                        // displaying type checking errors
	                        detail.append(FileConstants.COLUMN_VALUE, column.getValue());
	                    }

	                    detail.append(FileConstants.COLUMN_CANONICAL_NAME, column.getCanonicalName());
	                    columnData.add(detail);
	                    break outerloop;
	                }
	            }
	        }
	        return columnData;
	    }
}
