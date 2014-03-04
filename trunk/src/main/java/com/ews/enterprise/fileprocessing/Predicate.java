package com.ews.enterprise.fileprocessing;

import java.io.Serializable;

public interface Predicate<T> extends Serializable {

    boolean apply(T t);

}
