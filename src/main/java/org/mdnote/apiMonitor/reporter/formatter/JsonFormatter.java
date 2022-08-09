package org.mdnote.apiMonitor.reporter.formatter;

import com.alibaba.fastjson.JSON;
import org.mdnote.apiMonitor.aggregator.AggregateResult;

/**
 * @author Rhythm-2019
 * @date 2022/8/5
 * @description JSON 格式化
 */
public class JsonFormatter implements Formatter {

    @Override
    public String format(AggregateResult aggregateResult) {
        return JSON.toJSONString(aggregateResult);
    }
}
