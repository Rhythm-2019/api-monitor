package org.mdnote.apiMonitor.eporter;

import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.eporter.terminal.Terminal;
import org.mdnote.apiMonitor.exception.AggregateException;
import org.mdnote.apiMonitor.exception.MetricStorageException;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServerMetric;
import org.mdnote.apiMonitor.storage.MetricStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description 聚合结果上报，
 */
@Slf4j
public class ImmediateReporter {

    protected MetricStorage metricStorage;

    protected Aggregator aggregator;

    protected Terminal terminal;

    protected String serverName;

    protected List<String> uris;

    /**
     * 构造方法
     *
     * @param serverName    服务名，用于作为存储标识
     * @param uris          uri集合，指定需要上报哪些 uri
     * @param metricStorage 从指定存储介质拉取数据
     * @param aggregator    聚合器，用于计算聚合结果
     * @param terminal      输出终端
     */
    public ImmediateReporter(String serverName, List<String> uris, MetricStorage metricStorage, Aggregator aggregator, Terminal terminal) {
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

                resultList.add(this.aggregator.aggregate(clientMetricList, serverMetricList, durationMillis));
            } catch (MetricStorageException e) {
                log.error("failed to get metric in {}, error is {}", uri, e.getMessage());
            } catch (AggregateException e) {
                log.error("failed to aggregate metric in {}, error is {}", uri, e.getMessage());
            }
        }
        this.terminal.output(resultList);
    }
}
