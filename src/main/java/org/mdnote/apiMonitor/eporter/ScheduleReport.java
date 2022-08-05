package org.mdnote.apiMonitor.eporter;

import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.storage.MetricStorage;
import org.mdnote.apiMonitor.viewer.Terminal;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description
 */
public class ScheduleReport extends Reporter {

    private final ScheduledExecutorService executor;

    public ScheduleReport(String serverName, List<String> uris, MetricStorage metricStorage, Aggregator aggregator, Terminal terminal) {
        super(serverName, uris, metricStorage, aggregator, terminal);
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void report(int durationMillis) {
        this.executor.scheduleAtFixedRate(() -> {
            super.report(durationMillis);
        }, 0, durationMillis, TimeUnit.MILLISECONDS);
    }
}
