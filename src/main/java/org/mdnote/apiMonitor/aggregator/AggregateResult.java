package org.mdnote.apiMonitor.aggregator;

import lombok.Data;
import lombok.ToString;
import org.mdnote.apiMonitor.aggregator.calculator.*;
import org.mdnote.apiMonitor.annotation.CalculateBy;

/**
 * @author Rhythm-2019
 * 聚合结果
 */
@Data
@ToString
public class AggregateResult {

    /**
     * 时间间隔
     */
    private long duration;

    /**
     * 客户端请求次数
     */
    @CalculateBy(ClientCountCalculator.class)
    private Integer clientCount;

    /**
     * 平均 RT
     */
    @CalculateBy(MeanRTCalculator.class)
    private Double meanRT;

    /**
     * 99 RT
     */
    @CalculateBy(P99RTCalculator.class)
    private Double p99RT;

    /**
     * 999 RT
     */
    @CalculateBy(P999RTCalculator.class)
    private Double p999RT;

    /**
     * 服务端处理次数
     */
    @CalculateBy(ServerCountCalculator.class)
    private Integer serverCount;

    /**
     * QPS
     */
    @CalculateBy(QpsCalculator.class)
    private Double qps;

    /**
     * 服务端平均处理时间
     */
    @CalculateBy(MeanServerSpendTimeCalculator.class)
    private Double meanServerSpendTime;

    /**
     * 服务端最大处理时间
     */
    @CalculateBy(MaxServerSpendTimeCalculator.class)
    private Double maxServerSpendTime;
}
