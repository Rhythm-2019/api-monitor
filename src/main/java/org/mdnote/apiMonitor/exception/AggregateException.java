package org.mdnote.apiMonitor.exception;

/**
 * @author Rhythm-2019
 * <p>
 * 聚合异常，会在聚合计算时抛出，用户也可以在聚合函数中手动抛出
 */
public class AggregateException extends RuntimeException {

    private String msg;

    public AggregateException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AggregateException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
