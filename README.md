
##  需求背景

我们希望设计开发一个小的框架，能够获取接口调用的各种统计信息，比如，响应时间的最大值（max）、最小值（min）、平均值（avg）、百分位值（percentile）、接口调用次数（count）、频率（tps） 等，并且支持将统计结果以各种显示格式（比如：JSON格式、网页格式、自定义显示格式等）输出到各种终端（Console命令行、HTTP网页、Email、日志文件、自定义输出终端等），以方便查看。

拆解一下：
1. 需要对接口进行打点，采集每一次响应时间和开始请求时间
1. 对响应时间、请求时间进行存储
1. 定时对响应时间、请求时间进行聚合分析
1. 姜聚合后的结果导出到终端


##  版本安排

V1.0

1. 对接口进行打点采集
2. 将数据存储到 Redis 中
3. 将结果展示到终端


##  重构代码

重构的定义：在不改变系统的共嗯的前提下对系统中的代码进行修改使其符合设计原则。
何时重构：持续重构，一边 Coding 一边重构
重构分类：大型重构（对系统设计、类定义、分层进行重构）和小型重构（对类中的方法定义进行重构）

单元测试是对重构的保障：
单元测试：开发者对自己编写的函数进行测试
编写单元测试的好处：
1. 重新审视自己的代码设计
2. 检查代码正确性、对边界条件进行测试、减少 bug
3. 方便重构
4. 方便其他人阅读你的代码


##  单元测试指导代码重构

下面是一个转账案例，用到了分布式锁 RedisDistributedLock 和 远程服务通讯 walletRpcService
```java
public class Transaction {
  private String id;
  private Long buyerId;
  private Long sellerId;
  private Long productId;
  private String orderId;
  private Long createTimestamp;
  private Double amount;
  private STATUS status;
  private String walletTransactionId;
  
  // ...get() methods...
  
  public Transaction(String preAssignedId, Long buyerId, Long sellerId, Long productId, String orderId) {
    if (preAssignedId != null && !preAssignedId.isEmpty()) {
      this.id = preAssignedId;
    } else {
      this.id = IdGenerator.generateTransactionId();
    }
    if (!this.id.startWith("t_")) {
      this.id = "t_" + preAssignedId;
    }
    this.buyerId = buyerId;
    this.sellerId = sellerId;
    this.productId = productId;
    this.orderId = orderId;
    this.status = STATUS.TO_BE_EXECUTD;
    this.createTimestamp = System.currentTimestamp();
  }
  
  public boolean execute() throws InvalidTransactionException {
    if ((buyerId == null || (sellerId == null || amount < 0.0) {
      throw new InvalidTransactionException(...);
    }
    if (status == STATUS.EXECUTED) return true;
    boolean isLocked = false;
    try {
      isLocked = RedisDistributedLock.getSingletonIntance().lockTransction(id);
      if (!isLocked) {
        return false; // 锁定未成功，返回false，job兜底执行
      }
      if (status == STATUS.EXECUTED) return true; // double check
      long executionInvokedTimestamp = System.currentTimestamp();
      if (executionInvokedTimestamp - createdTimestap > 14days) {
        this.status = STATUS.EXPIRED;
        return false;
      }
      WalletRpcService walletRpcService = new WalletRpcService();
      String walletTransactionId = walletRpcService.moveMoney(id, buyerId, sellerId, amount);
      if (walletTransactionId != null) {
        this.walletTransactionId = walletTransactionId;
        this.status = STATUS.EXECUTED;
        return true;
      } else {
        this.status = STATUS.FAILED;
        return false;
      }
    } finally {
      if (isLocked) {
       RedisDistributedLock.getSingletonIntance().unlockTransction(id);
      }
    }
  }
}
```

上面的代码可测试性比较差，我们一边编写测试用例，一边发现问题一百年重构

测试正常逻辑时。遇到的第一个问题是分布式锁。我们不可能为了编写单元测试搭建 Redis，所以需要**解以来**，也就是 Mock
这里的 RedisDistributedLock 就很难 mock 了，这指导我们应该进行重构
```java
public class TransactionLock {
  public boolean lock(String id) {
    return RedisDistributedLock.getSingletonIntance().lockTransction(id);
  }
  
  public void unlock() {
    RedisDistributedLock.getSingletonIntance().unlockTransction(id);
  }
}

public class Transaction {
  //...
  private TransactionLock lock;
  
  public void setTransactionLock(TransactionLock lock) {
    this.lock = lock;
  }
 
  public boolean execute() {
    //...
    try {
      isLocked = lock.lock();
      //...
    } finally {
      if (isLocked) {
        lock.unlock();
      }
    }
    //...
  }
}
```
接下来是远程服务调用 WalletRpcService，这里违背了依赖倒转原则，可测试性差，应该把 new 的动作放到外面，然后使用继承进行 mock
```java
public class MockWalletRpcServiceOne extends WalletRpcService {
    public String moveMoney(Long id, Long fromUserId, Long toUserId, Double amount) {
        return "123bac";
    }
}

public class MockWalletRpcServiceTwo extends WalletRpcService {
    public String moveMoney(Long id, Long fromUserId, Long toUserId, Double amount) {
        return null;
    }
}
```

接着是过期判断 `executionInvokedTimestamp - createdTimestap > 14days`，这种未决行为需要抽象到另一个方法才能被 mock
```java
public class Transaction {

  protected boolean isExpired() {
    long executionInvokedTimestamp = System.currentTimestamp();
    return executionInvokedTimestamp - createdTimestamp > 14days;
  }
  
  public boolean execute() throws InvalidTransactionException {
    //...
      if (isExpired()) {
        this.status = STATUS.EXPIRED;
        return false;
      }
    //...
  }
}
```
这样的未决行为成为 Anti-Patterns
1. 未决行为：随机数、时间过期都要进行抽象
2. 包含全局变量，需要提供一个 reset 的方法
3. 静态方法：类似上面分布式锁这种要重构一下，其他简单逻辑就没有必要了
4. 复杂继承：继承 mock 的时候要注意父类的实现，我们可以多用组合少用继承来方便 mock 复杂的方法
5. 高耦合代码：耦合了太多依赖时编写单元测试比较费劲


