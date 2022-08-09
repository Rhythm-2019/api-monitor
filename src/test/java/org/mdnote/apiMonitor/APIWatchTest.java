package org.mdnote.apiMonitor;

import org.junit.Test;
import org.mdnote.apiMonitor.aggregator.AggregateResult;
import org.mdnote.apiMonitor.aggregator.Aggregator;
import org.mdnote.apiMonitor.collecter.DefaultMetricCollector;
import org.mdnote.apiMonitor.reporter.ScheduleReport;
import org.mdnote.apiMonitor.reporter.formatter.JsonFormatter;
import org.mdnote.apiMonitor.reporter.terminal.ConsoleTerminal;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.storage.local.LocalStorage;

import java.util.Arrays;
import java.util.Random;

public class APIWatchTest {

    @Test
    public void test() throws InterruptedException {

        LocalStorage localStorage = new LocalStorage();
//        InfluxDBIMetricStorage influxDBIMetricStorage = new InfluxDBIMetricStorage();
        DefaultMetricCollector collector = new DefaultMetricCollector(Arrays.asList(localStorage));
        Random random = new Random();
        new Thread(() -> {
            while (true) {
                ClientMetric metric = new ClientMetric();
                metric.setUri("111");
                metric.setServerName("server-test");
                metric.setRt(random.nextInt(100));
                collector.mark(metric);
                System.out.println(metric);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();

        ScheduleReport scheduleReport = new ScheduleReport("server-test", Arrays.asList("111", "222"), localStorage, new Aggregator(AggregateResult.class), new ConsoleTerminal(new JsonFormatter()));
        scheduleReport.start(3_000, 10_000);

        Thread.currentThread().join();

    }

}
