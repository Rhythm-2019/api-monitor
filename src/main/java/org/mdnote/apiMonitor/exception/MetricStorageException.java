package org.mdnote.apiMonitor.exception;

/**
 * @author Rhythm-2019
 * <p>
 * 指标存储异常，会在数据库连接、或者内存溢出时抛出
 */
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
