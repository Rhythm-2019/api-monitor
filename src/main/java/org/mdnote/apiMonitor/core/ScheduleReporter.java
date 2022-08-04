package org.mdnote.apiMonitor.core;

import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiMonitor.AggregateResult;
import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.storage.IMetricStorage;
import org.mdnote.apiMonitor.storage.MetricStorageException;
import org.mdnote.apiMonitor.viewer.ITerminal;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Rhythm-2019
 *
 * 该类以主动方式周期性进行指标聚合上报
 *
 * 用户可以通过周期性聚合，将聚合数据通过 Email 或其他方式进行消息推送
 *
 *
 */
@Slf4j
public class ScheduleReporter {

//    private final int DAY_HOURS_IN_SECONDS = 24 * 60 * 60;

    private ScheduledExecutorService executor;
    private IMetricStorage metricStorage;
    private Aggregator aggregator;
    private ITerminal terminal;

    public ScheduleReporter(IMetricStorage metricStorage, Aggregator aggregator, ITerminal terminal) {
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start(long periodSecond, long durationSecond) {
        this.executor.scheduleAtFixedRate(() -> {
            long endTimeMillis = System.currentTimeMillis();
            long durationMillis = durationSecond * 1000;
            long startTimeMillis = endTimeMillis - durationMillis;
            Map<String, List<Metric>> metricsMap = null;
            try {
                metricsMap = this.metricStorage.getMetric(startTimeMillis, endTimeMillis);
            } catch (MetricStorageException e) {
                log.error("failed to aggregate metric, error is {}", e.getMessage());
                return;
            }
            List<AggregateResult> aggregateResultList = this.aggregator.aggregate(metricsMap, durationSecond);
            this.terminal.output(aggregateResultList);

        }, 0, periodSecond, TimeUnit.SECONDS);
    }
}
