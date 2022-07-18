package org.mdnote.apiWatch.report;

import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiWatch.AggregateResult;
import org.mdnote.apiWatch.Metric;
import org.mdnote.apiWatch.aggregator.Aggregator;
import org.mdnote.apiWatch.storage.IMetricStorage;
import org.mdnote.apiWatch.storage.MetricStorageException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class Report {

    private ScheduledExecutorService executor;
    private IMetricStorage metricStorage;
    private Aggregator aggregator;
    public Report(IMetricStorage metricStorage, Aggregator aggregator) {
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start(long periodSecond, long durationSecond) {
        this.executor.scheduleAtFixedRate(() -> {
            long endTimeMillis = System.currentTimeMillis();
            long startTimeMillis = endTimeMillis - durationSecond * 1000;
            Map<String, List<Metric>> metricsMap = null;
            try {
                metricsMap = this.metricStorage.getMetric(startTimeMillis, endTimeMillis);
            } catch (MetricStorageException e) {
                log.error("failed to aggregate metric, error is {}", e.getMessage());
                return;
            }

            metricsMap.forEach((apiName, metrics) -> {
                AggregateResult aggregateResult = this.aggregator.aggregate(metrics, durationSecond);

                aggregateResult.setApiName(apiName);
                aggregateResult.setDurationSecond(durationSecond);

                this.report(aggregateResult);
            });
        }, 0, periodSecond, TimeUnit.SECONDS);
    }
    protected abstract void report(AggregateResult aggregateResult);
}
