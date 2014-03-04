package com.ews.enterprise.integration.dao;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public interface FileStorageDAO {
	void saveFile(byte[] fileContent, String fileName, BasicDBObject metaData) throws Exception;
	void saveData(final List<DBObject> list, final String collectionName) throws Exception;
}
