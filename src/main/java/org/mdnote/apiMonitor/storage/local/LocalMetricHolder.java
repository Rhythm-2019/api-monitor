package org.mdnote.apiMonitor.storage.local;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.mdnote.apiMonitor.metric.Metric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description 本地指标缓存
 */
@Slf4j
public class LocalMetricHolder<T extends Metric> {

    private HashMap<String, SkipListMap<Long, T>> holder;
    private ReadWriteLock lock;
    private ScheduledExecutorService clearThread;

    public LocalMetricHolder(int clearDelay, TimeUnit timeUnit) {
        this.holder = new HashMap<>();
        this.lock = new ReentrantReadWriteLock();

        // schedule clear
        this.clearThread = Executors.newSingleThreadScheduledExecutor();
        clearThread.schedule(() -> {
            log.info("begin clear memory metric");
            Stopwatch stopwatch = Stopwatch.createStarted();

            try {
                this.lock.writeLock().lock();
                long clearBeforeTime = System.currentTimeMillis() - 30 * 60 * 100;
                this.holder.forEach((key, metricMap) -> {
                    Set<Long> series = metricMap.keySet();
                    for (Long timestamp : series) {
                        if (timestamp < clearBeforeTime) {
                            metricMap.remove(timestamp);
                        }
                    }
                });
            } finally {
                this.lock.writeLock().unlock();
            }

            stopwatch.stop();
            log.info("finish clear memory metric, spend {} seconds", stopwatch.elapsed().getSeconds());

        }, clearDelay, timeUnit);

    }

    public void put(T metric) {
        try {
            this.lock.writeLock().lock();
            String key = this.getKey(metric.getServerName(), metric.getUri());
            this.holder.putIfAbsent(key, new SkipListMap<>());
            this.holder.get(key).put(metric.getTimestamp(), metric);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    public List<T> list(String serverName, String uri, long startTimeInMillis, long endTimeInMillis) {

        try {
            this.lock.readLock().lock();
            SkipListMap<Long, T> metricSkipList = this.holder.get(getKey(serverName, uri));
            if (metricSkipList == null) {
                return new ArrayList<>();
            }
            return new ArrayList<>(metricSkipList.subMap(startTimeInMillis, endTimeInMillis).values());
        } finally {
            this.lock.readLock().unlock();
        }
    }

    private String getKey(String servername, String uri) {
        return servername + ":" + uri;
    }

}
