# 4 MQ

## 4.1 初识MQ

### 4.1.1 同步通讯

打视频电话。时效性比较强，可以立即得到结果。微服务调用Feign的调用就是同步方式，但是存在一定的问题。

**存在的问题**

*  性能下降，吞吐量下降
* 代码耦合程度比较高，不易产品的升级以及功能模块的增加
* 硬件资源浪费
* 级联失败的问题

### 4.1.2 异步通讯

微信聊天。异步调用常见的实现就是事件驱动模式。

![](https://shepi-1308499968.cos.ap-chengdu.myqcloud.com/img/事件驱动模式.png)

**优点**：

* 解除了服务之间的耦合
* 性能提升，吞吐量提高
* 服务之间没有强依赖，不用担心级联失败的问题
* 流量削

### 4.1.3 MQ常用框架

MQ，中文就是消息队列，字面意思就是存放消息的队列，也就是事件驱动架构的`broker`

**常见消息队列的对比**

![](https://shepi-1308499968.cos.ap-chengdu.myqcloud.com/img/消息队列对比.png)



## 4.2 RabbitMQ快速入门

### 4.2.1 RabbitMQ概述和安装

```shell
docker run \
-e RABBITMQ_DEFAULT_USER=root \
-e RABBITMQ_DEFAULT_PASS=root \
--name mq \
--hostname mq1 \
-p 15672:15672 \
-p 5672:5672 \
-d rabbitmq:3.9
```



### 4.2.2 常见消息模型

总共有五种消息模型：简单队列，工作队列，发布订阅……

### 4.2.3 快速入门

## 4.3 SpringAMQP

AMQP：高级消息队列协议,应用程序之间传递消息的开放标准。

### 4.3.1 简单队列模型

* 引入依赖
* 在yaml配置文件中编辑mq连接消息
* 在consumer服务中新建一个类，编写消费逻辑，添加`Component`注解，在方法上添加`@RabbitListener`注解

> 注意：消息一旦消费就会从队列中删除，RabbitMQ没有消息回溯功能

### 4.3.2 工作队列模型

![](https://shepi-1308499968.cos.ap-chengdu.myqcloud.com/img/工作队列模型.png)



**消息预取机制：**

当有大量的消息涌入到消息队列中的时候，消费者的channel通道会预先从队列中取消息，然后两个人再慢慢消费。

修改消费预取的设置：【没有金刚钻就别揽瓷器活】

```yaml
spring:
  rabbitmq:
    listener:
      simple:
        prefetch: 1 # 每次只能取一个消息，处理完之后才能获取下一个消息
```



### 4.3.3 发布订阅模型

发布订阅模式允许将同一个消息发送给多个消费者，实现方式就是加入了交换机。只负责消息的转发而不是存储，一旦路由失败则消息丢失。

![](https://shepi-1308499968.cos.ap-chengdu.myqcloud.com/img/发布订阅模式.png)

**交换机的作用**：

* 接收publisher发送的消息
* 将消息按照规则路由到与之绑定的队列
* 不能缓存消息，路由失败，消息丢失
* FanoutExchange会将消息路由到每一个与之绑定的队列

#### 4.3.3.1 Fanout

![](https://shepi-1308499968.cos.ap-chengdu.myqcloud.com/img/fanout.png)

就是广播模式，交换机会将接受到的消息路由到每一个和其绑定的队列。

#### 4.3.3.2 Direct

![](https://shepi-1308499968.cos.ap-chengdu.myqcloud.com/img/direct.png)

将接收到的消息根据路由规则路由到指定的queue，因此称为路由模式。

* 每一个queue都和exchange设置一个bindingkey
* 发布者发送消息的时候，指定消息的routingKey
* exchange将消息路由到bindingKey和routingKey一致的队列

#### 4.3.3.3 Topic

和DirectExchange类似，区别在于routingKey必须是多个单词的列表，并且以`.`进行分割。

Queue和Exchange指定BindingKey的时候可以使用通配符。

### 4.3.4 消息转换器

SpringAMQP中消息队列的序列化和反序列化是怎样实现的？

* 利用MessageConvert实现，默认是JDK的序列化
* 注意发送方和接收方必须使用相同的MessageConverter