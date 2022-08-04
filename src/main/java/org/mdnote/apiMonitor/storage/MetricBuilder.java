package org.mdnote.apiMonitor.storage;

import org.mdnote.apiMonitor.metric.Metric;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description 工厂方法
 */
public class MetricBuilder {

    private static List<IBuilder> builderList = new ArrayList<>();

    static {
        builderList.add(new RTMerticBuilder());
        builderList.add(new ServerSpendTimeMetricBuilder());
        builderList.add(new HttpStatusCodeMetricBuilder());
    }

    public List<MetricEntity> build(Metric metric) {
        List<MetricEntity> metricEntityList = new ArrayList<>();
        builderList.forEach(iBuilder -> {
            MetricEntity metricEntity = iBuilder.build(metric);
            if (metricEntity != null) {
                metricEntityList.add(metricEntity);
            }
        });

        return metricEntityList;
    }

    private interface IBuilder {
        MetricEntity build(Metric metric);
    }

    private static class RTMerticBuilder implements IBuilder {
        @Override
        public MetricEntity build(Metric metric) {
            if (metric.getRt() == 0) {
                return null;
            }
            MetricEntity metricEntity = new MetricEntity("RT", metric.getTimestamp());
            metricEntity.addFiled("uri", metric.getUri());
            metricEntity.addFiled("server-name", metric.getServerName());
            return metricEntity;
        }
    }

    private static class ServerSpendTimeMetricBuilder implements IBuilder {
        @Override
        public MetricEntity build(Metric metric) {
            if (metric.getSpendTime() == 0) {
                return null;
            }
            MetricEntity metricEntity = new MetricEntity("Server-spend-time", metric.getTimestamp());
            metricEntity.addFiled("uri", metric.getUri());
            metricEntity.addFiled("server-name", metric.getServerName());
            return metricEntity;
        }
    }

    private static class HttpStatusCodeMetricBuilder implements IBuilder {
        @Override
        public MetricEntity build(Metric metric) {
            if (metric.getHttpStatusCode() == 0) {
                return null;
            }
            MetricEntity metricEntity = new MetricEntity("http-status-code", metric.getHttpStatusCode());
            metricEntity.addFiled("uri", metric.getUri());
            metricEntity.addFiled("server-name", metric.getServerName());
            return metricEntity;
        }
    }
}
