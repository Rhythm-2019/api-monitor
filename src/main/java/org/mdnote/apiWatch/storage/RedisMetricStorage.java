package org.mdnote.apiWatch.storage;

import java.util.*;

import org.mdnote.apiWatch.Metric;
import org.mdnote.apiWatch.util.ByteUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Rhythm-2019
 * @date 2022-07-17
 *
 * 时序模型  METRIC [resp_time: 000; tag apuBane] ts
 * redis 可以使用 Hash（支持 ts 查询指标） 和 ZSet（支持范围查询，store 为时间错） 时限时序数据存储，使用 EXEC、MULRI 保证原子性
 * RedisTimeSeries 支持范围聚合，可以减少数据传输
 */
public class RedisMetricStorage implements IMetricStorage {

    private static final int RETRY_CONNECT_TIME = 3;

    private static final String RESP_TIME_KEY = "resp_time";
    private static final String CUR_TIME_KEY = "cur_time";

    private String ip;
    private int port;
    private String password;

    private JedisPool jedisPool;

    public RedisMetricStorage(String ip, int port, String password) {
        this.ip = ip;
        this.port = port;
        this.password = password;
    }

    private Optional<Jedis> getConnect() {
        if (jedisPool == null) {
            int retry = RETRY_CONNECT_TIME;
            while (retry > 0) {
                try {
                    this.jedisPool = new JedisPool(this.ip, this.port);
                    break;
                } catch (Exception e) {
                    retry--;
                    continue;
                }
            }
            if (retry > 0) {
                return Optional.of(jedisPool.getResource());
            }
        }
        return Optional.empty();
    }

    public void saveMetric(Metric metric) throws MetricStorageException {
        // use hash， key is metric-name, field is crc32(apiName), value is metric-value
        Optional<Jedis> optional = this.getConnect();
        if(optional.isPresent()) {
            try {
                optional.get().hset(RESP_TIME_KEY.getBytes(), metric.getApiName().getBytes(), ByteUtil.toBytes(metric.getResponseTime()));
                optional.get().hset(CUR_TIME_KEY.getBytes(), metric.getApiName().getBytes(), ByteUtil.toBytes(metric.getCurrentTimestamp()));
            } catch (Exception e) {
                throw new MetricStorageException("save metric failed", e);
            }
        } else {
            throw new MetricStorageException("failed to get connection");
        }
    }

    public Map<String, List<Metric>> getMetric(long startTimeInMillis, long endTimeInMillis) {

        Optional<Jedis> optional = this.getConnect();
        if (optional.isPresent()) {
            Map<String, List<Metric>> result = new HashMap<>();

            Map<String, String> respTimeApiNameMap = optional.get().hgetAll(RESP_TIME_KEY);
            Map<String, String> curTimeApiNameMap = optional.get().hgetAll(CUR_TIME_KEY);

            List<Metric> metricList = new ArrayList<>();
            curTimeApiNameMap.forEach((apiName, curTimeStr) -> {
                String respTimeStr = respTimeApiNameMap.get(apiName);


                Metric metric = new Metric();
                metric.setApiName(apiName);
                metric.setCurrentTimestamp(ByteUtil.toLong(curTimeStr.getBytes()));
                metric.setResponseTime(ByteUtil.toLong(respTimeStr.getBytes()));

                metricList.add(metric);
            });

//            result.put()
        }
        return null;
    }

    public List<Metric> getMetric(String apiName, long startTimeInMillis, long endTimeInMillis) {
        return null;
    }
}
