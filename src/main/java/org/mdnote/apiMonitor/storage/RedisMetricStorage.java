package org.mdnote.apiMonitor.storage;

import java.util.*;

import com.redislabs.redistimeseries.RedisTimeSeries;
import org.mdnote.apiMonitor.metric.ClientMetric;
import org.mdnote.apiMonitor.metric.ServiceMetric;

/**
 * @author Rhythm-2019
 *
 * 时序模型  METRIC [resp_time: 000; tag apuBane] ts
 * redis 可以使用 Hash（支持 ts 查询指标） 和 ZSet（支持范围查询，store 为时间错） 时限时序数据存储，使用 EXEC、MULRI 保证原子性
 * RedisTimeSeries 支持范围聚合，可以减少数据传输
 *
 */
public class RedisMetricStorage implements IMetricStorage {

    // TODO 补充 Redis 实现

    private static final int RETRY_CONNECT_TIME = 3;

    private static final String RESP_TIME_KEY = "resp_time";
    private static final String CUR_TIME_KEY = "cur_time";

    private RedisTimeSeries redisTimeSeries;

    public RedisMetricStorage(String ip, int port, String password) {
        this.redisTimeSeries = getRedisTimeSeries(ip, port, password);
    }

    private RedisTimeSeries getRedisTimeSeries(String ip, int port, String password) {
        if (password == null || password.isEmpty()) {
            return new RedisTimeSeries(ip, port, 500, 100);
        } else {
            return new RedisTimeSeries(ip, port, 500, 100, password);
        }
    }

    public void saveMetric(Metric metric) throws MetricStorageException {
        // use hash， key is metric-name, field is crc32(apiName), value is metric-value
        try {
            Map<String, String> labels = Collections.singletonMap("apiName", metric.getUri());
            this.redisTimeSeries.create("", 60*10 /*10min*/, labels);
        } catch (Exception e) {
            throw new MetricStorageException("save metric failed", e);
        }
    }


    @Override
    public void saveMetric(ClientMetric metric) throws MetricStorageException {

    }

    @Override
    public void saveMetric(ServiceMetric metric) throws MetricStorageException {

    }

    @Override
    public Map<String, ClientMetric> getClientMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException {
        return null;
    }

    @Override
    public Map<String, ServiceMetric> getServerMetric(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) throws MetricStorageException {
        return null;
    }
}
