package org.mdnote.apiMonitor.storage;

import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.jetbrains.annotations.NotNull;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.Metric;
import org.mdnote.apiMonitor.metric.MetricAnnotationUtil;
import org.mdnote.apiMonitor.metric.ServiceMetric;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Rhythm-2019
 * @date 2022/8/4
 * @description InfluxDB 存储
 *
 * InfluxDB：measurement 相当于 table，这里定义为 [server-name]:[uri]:[client|server]
 *           field 相当于字段，比如 RT、ServerSpendTime 等
 */
@Slf4j
public class InfluxDBIMetricStorage implements IMetricStorage {

    private InfluxDB influxDB;

    public InfluxDBIMetricStorage(@NotNull String serverURL, @NotNull String username, String password, @NotNull String database) {
        this.influxDB = InfluxDBFactory.connect(serverURL, username, password);
        this.influxDB.query(new Query("CREATE DATABASE IF NOT EXISTS " + database));
        this.influxDB.setDatabase(database);
    }

    // TODO 异常处理写好一点
    @Override
    public void saveMetric(@NotNull ClientMetric metric) throws MetricStorageException {
        this.save(String.format("%s:%s:%s", metric.getServerName(), metric.getUri(), "client"), metric);
    }

    @Override
    public void saveMetric(@NotNull ServiceMetric metric) throws MetricStorageException {
        this.save(String.format("%s:%s:%s", metric.getServerName(), metric.getUri(), "server"), metric);
    }

    private void save(String measurement, Metric metric) {

        HashMap<String, Object> fields = new HashMap<>();
        for (Map.Entry<String, Field> entry : MetricAnnotationUtil.getFieldMap(metric.getClass()).entrySet()) {
            try {
                fields.put(entry.getKey(), entry.getValue().get(metric));
            } catch (IllegalAccessException e) {
                log.error("reflect error", e);
            }
        }
        this.influxDB.write(Point.measurement(measurement)
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .fields(fields)
                .build());
    }
    private <T extends Metric> Map<String, T> query(String influxQL, Class<T> targetClz) throws MetricStorageException {
        QueryResult queryResult = this.influxDB.query(new Query(influxQL));

        if (queryResult.hasError()) {
            throw new MetricStorageException(queryResult.getError());
        }
        QueryResult.Series series = queryResult.getResults().get(0).getSeries().get(0);
        List<String> columns = series.getColumns();
        List<List<Object>> table = series.getValues();

        HashMap<String, T> result = new HashMap<>();
        Map<String, Field> fieldMap = MetricAnnotationUtil.getFieldMap(targetClz);

        for (List<Object> row : table) {

            for (int j = 0; j < row.size(); j++) {
                try {
                    T metric = targetClz.newInstance();
                    String columnName = columns.get(j);
                    if ("time".equals(columnName)) {
                        String tsFieldName = MetricAnnotationUtil.timestampFieldName(metric);
                        if (tsFieldName != null) {
                            ClientMetric.class.getField(tsFieldName).set(metric, row.get(0));
                        }
                    } else {
                        if (fieldMap.containsKey(columnName)) {
                            fieldMap.get(columnName).setAccessible(true);
                            fieldMap.get(columnName).set(metric, row);
                        }
                    }
                    result.put(metric.getUri(), metric);
                } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
                    log.error("reflect error!", e);
                }
            }
        }
        return result;

    }
    @Override
    public Map<String, ClientMetric> getClientMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException {
        return this.query(String.format("SELECT * FROM %s:%s:client WHERE time >= %d AND time <= %d",
                serverName, uri, startTimeInMillis, endTimeInMillis), ClientMetric.class);
    }

    @Override
    public Map<String, ServiceMetric> getServerMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException {
        return this.query(String.format("SELECT * FROM %s:%s:server WHERE time >= %d AND time <= %d",
                serverName, uri, startTimeInMillis, endTimeInMillis), ServiceMetric.class);
    }


}
