
##  需求背景

我们希望设计开发一个小巧的框架，能够获取接口调用的各种统计信息，比如，响应时间的最大值（max）、最小值（min）、平均值（avg）、百分位值（percentile）、接口调用次数（count）、频率（tps） 等，并且支持将统计结果以各种显示格式（比如：JSON格式、网页格式、自定义显示格式等）输出到各种终端（Console命令行、HTTP网页、Email、日志文件、自定义输出终端等），以方便查看。

##  需求分析与代码设计（核心）

经过该需求总结需求开发流程：OOA -> OOD -> OOP
1. 根据需求列表定义类
2. 定义类的属性和方法
3. 梳理类与类之间的交互关系
4  定义入口

###  事前：捋清楚需求

了解需求：
1. 需要对接口的相应时间进行打点采集
2. 采集后需要存储到某个介质中
3. 周期性/立即性对指标进行聚合分析
4. 聚合分析后的结果进行格式化
5. 将格式化后的结果输出到终端

了解技术重点和难点：
1. 应用层 API 监控相关参数、采集方式和计算公式：[ISSUE 1](https://github.com/Rhythm-2019/api-monitor/issues/1#issue-1325563810)
2. 指标数据的存储选型：issue

###  第一步：根据需求列表找到类的定义

根据上面的需求列表，提取类定义（可以找名词）
* 打点采集：Collector
* 存储介质：Storage
* 聚合工具：Aggregate
* 输出终端：Terminal
* 输出格式化：Formatter
* 周期上报/立即上报：ScheduleReport、ImmediateReporter

###  第二步：定义类的属性和方法

根据上面的需求列表为类定义属性和方法（可以找动词）
* Collector：
    * 属性：（无明显需要定义的属性、延后到第三步再思考）
    * 方法：collect、asynCollect
* Storage：
    * 属性：不同介质的属性，比如 Redis 中的账号密码等
    * 方法：save、list、listRange
* Aggregate：
    * 属性：（无明显需要定义的属性、延后到第三步再思考）
    * 方法：aggregate
* Terminal：
    * 属性：输出终端的相关属性，比如 URL 等
    * 方法：output
* Formatter：
    * 属性：无
    * 方法：format

###  第三步：梳理类和类之间的关系

* 用户直接与 Collector 打交道，Collector 需要依赖 Storage 存储指标数据 
，Reporter 负责任务调度，统筹聚合统计工作。需要依赖 Storage 获取数据，Aggregate 聚合数据、Terminal 输出数据，
* Terminal 构造时需要依赖 Format
* Aggregate 不与 Storage 关联，Aggregate 只做聚合分析，夫负责拉取指标数据


###  第四步：编写入口程序

Reporter 定时或者周期触发


##  Usage




##  环境搭建

Redis 时序数据库搭建（RedisTimeSeries）

```bash
$ vagrant init centos/7
$ vagrant up

# install  redis
$ sdocker run -p 6379:6379 -it --rm redislabs/redistimeseries


https://cloud.tencent.com/developer/news/491464
```

