package org.mdnote.apiMonitor.collecter;

import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;

/**
 * @author Rhythm-2019
 * <p>
 * 采集器抽象接口，可可以使用同步或者异步的方式进行指标采集
 * <p>
 * 可以使用 AOP 的方式在你的应用程序中织入异步记录的逻辑，采集后的数据经过
 * 周期性 (@code ScheduleReport) 或立即性（@code ImmedicateReport）聚
 * 合上报
 */
public interface SyncMetricCollector extends MetricCollector {
    /**
     * 同步记录客户端采集指标
     *
     * @param metric 指标
     */
    void mark(ClientMetric metric);

    /**
     * 同步记录服务器采集指标
     *
     * @param metric 指标
     */
    void mark(ServerMetric metric);
}