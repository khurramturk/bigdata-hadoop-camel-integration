package com.ews.enterprise.fileprocessing;

import java.io.IOException;

public interface RecordReader {

    String[] read() throws IOException;

    void close() throws IOException;
}
