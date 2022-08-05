package org.mdnote.apiMonitor.eporter;

import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.exception.MetricStorageException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;
import org.mdnote.apiMonitor.storage.MetricStorage;
import org.mdnote.apiMonitor.viewer.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description
 */
@Slf4j
public class Reporter {

    private MetricStorage metricStorage;
    private Aggregator aggregator;
    private Terminal terminal;

    private String serverName;
    private List<String> uris;

    public Reporter(String serverName, List<String> uris, MetricStorage metricStorage, Aggregator aggregator, Terminal terminal) {
        this.serverName = serverName;
        this.uris = uris;
        this.metricStorage = metricStorage;
        this.aggregator = aggregator;
        this.terminal = terminal;
    }

    public void report(int durationMillis) {
        long endTimeMillis = System.currentTimeMillis();
        long startTimeMillis = endTimeMillis - durationMillis;

        List<AggregateResult> resultList = new ArrayList<>();
        for (String uri : this.uris) {
            List<ClientMetric> clientMetricList = null;
            List<ServerMetric> serverMetricList = null;
            try {
                serverMetricList = this.metricStorage.getServerMetric(serverName, uri, startTimeMillis, endTimeMillis);
                clientMetricList = this.metricStorage.getClientMetric(serverName, uri, startTimeMillis, endTimeMillis);
            } catch (MetricStorageException e) {
                log.error("failed to aggregate metric, error is {}", e.getMessage());
                return;
            }
            resultList.add(this.aggregator.aggregate(clientMetricList, serverMetricList, durationMillis));
        }
        this.terminal.output(resultList);
    }
}
