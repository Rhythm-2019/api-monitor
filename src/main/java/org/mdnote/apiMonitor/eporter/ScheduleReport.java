package org.mdnote.apiMonitor.eporter;

import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.eporter.terminal.Terminal;
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

    public ScheduleReport(String serverName, List<String> uris, MetricStorage metricStorage, Aggregator aggregator, Terminal terminal) {
        immediateReporter = new ImmediateReporter(serverName, uris, metricStorage, aggregator, terminal);
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void start(int periodMillis, int durationMillis) {
        this.executor.scheduleAtFixedRate(() -> {
            this.immediateReporter.report(durationMillis);
        }, periodMillis, periodMillis, TimeUnit.MILLISECONDS);
    }
}
