package com.ews.enterprise.fileprocessing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

public interface FileTemplate extends Serializable {

    List<RowTemplate> getRowTemplates();

    RecordReader newRecordReader(InputStream in) throws IOException;

    RecordWriter newRecordWriter(OutputStream out) throws IOException;

    FileTemplate getNormalizedFileTemplate();
}
