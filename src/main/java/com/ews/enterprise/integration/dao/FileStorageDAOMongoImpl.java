package com.ews.enterprise.integration.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class FileStorageDAOMongoImpl implements FileStorageDAO {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Mongo mongo;
	private final String dbName;

	@Inject
	public FileStorageDAOMongoImpl(final Mongo mongo,
			@Named("FileArchiveDBName") final String dbName) {
		this.mongo = mongo;
		this.dbName = dbName;
		// buildIndicies();

	}

	@Override
	public final void saveFile(final byte[] fileContent, final String fileName,
			final BasicDBObject metaData) throws Exception {
		try {
			DB database = mongo.getDB(dbName);
			GridFS grid = new GridFS(database);
			GridFSInputFile inputFile = grid.createFile(fileContent);

			inputFile.setMetaData(metaData);
			inputFile.setFilename(fileName);

			inputFile.save();
		} catch (Exception ex) {
			throw new Exception("Mongo: Error Saving file - " + fileName, ex);
		}
	}

	public final void saveData(final List<DBObject> list, final String collectionName)
	        throws Exception {
	        try {
	            // TODO Indexes need to be set and applied
	            DBCollection collection = mongo.getDB(dbName).getCollection(collectionName);
	            collection.insert(list);
	        } catch (Exception ex) {
	            logger.error("Mongo: Error Saving Imported Data List", ex);
	            throw new Exception("Mongo: Error Saving Imported Data List", ex);
	        }
	    }
}
