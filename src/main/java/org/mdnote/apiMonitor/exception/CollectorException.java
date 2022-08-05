package org.mdnote.apiMonitor.exception;

public class CollectorException extends Exception {

    private String msg;

    public CollectorException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CollectorException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
