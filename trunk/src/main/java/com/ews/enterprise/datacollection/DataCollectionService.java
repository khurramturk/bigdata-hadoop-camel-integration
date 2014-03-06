package com.ews.enterprise.datacollection;

import java.util.List;

public interface DataCollectionService {

    // interface
    Object getDcr(String type);

    boolean save(Object dcr);

    boolean saveAll(List<Object> dcrList);
}
