package org.mdnote.apiWatch.storage;

import java.util.concurrent.Executors;

public class MetricStorageException extends Exception {

    private String msg;

    public MetricStorageException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MetricStorageException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
