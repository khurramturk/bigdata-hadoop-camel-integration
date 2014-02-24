package com.ews.enterprise.integration.dao;

import com.mongodb.BasicDBObject;

public interface FileStorageDAO {
	void saveFile(byte[] fileContent, String fileName, BasicDBObject metaData) throws Exception;
}
