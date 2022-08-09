package org.mdnote.apiMonitor.reporter;

import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.reporter.terminal.Terminal;
import org.mdnote.apiMonitor.storage.MetricStorage;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description
 */
public class ScheduleReport {

    private final ScheduledExecutorService executor;
    private ImmediateReporter immediateReporter;

    /**
     * 构造方法
     *
     * @param serverName    服务名，用于作为存储标识
     * @param uris          uri集合，指定需要上报哪些 uri
     * @param metricStorage 从指定存储介质拉取数据
     * @param aggregator    聚合器，用于计算聚合结果
     * @param terminal      输出终端
     */
    public ScheduleReport(String serverName, List<String> uris, MetricStorage metricStorage, Aggregator aggregator, Terminal terminal) {
        immediateReporter = new ImmediateReporter(serverName, uris, metricStorage, aggregator, terminal);
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * 开始采集
     * @param periodMillis 定时任务
     * @param durationMillis 统计时间间隔
     */
    public void start(int periodMillis, int durationMillis) {
        this.executor.scheduleAtFixedRate(() -> {
            this.immediateReporter.report(durationMillis);
        }, periodMillis, periodMillis, TimeUnit.MILLISECONDS);
    }
}
