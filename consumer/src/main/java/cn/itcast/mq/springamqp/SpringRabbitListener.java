package cn.itcast.mq.springamqp;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class SpringRabbitListener {

    /*@RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到的消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(20);
    }*/

    // @RabbitListener(queues = "simple.queue")
    // public void listenWorkQueue2(String msg) throws InterruptedException {
    //     System.err.println("消费者2接收到的消息：【" + msg + "】" + LocalTime.now());
    //     Thread.sleep(20);
    // }

    @RabbitListener(queues = "fanout.queue1")
    public void listenFanQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到的消息：【" + msg + "】" + LocalTime.now());
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanQueue2(String msg) throws InterruptedException {
        System.out.println("消费者2接收到的消息：【" + msg + "】" + LocalTime.now());
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "javadong.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者接收到的direct.queue1的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "javadong.direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String msg) {
        System.out.println("消费者接收到的direct.queue2的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "javadong.topic", type = "topic"),
            key = "china.#"
    ))
    public void listenTopicQueue1(String msg) {
        System.out.println("消费者接收到的topic.queue1的消息：【" + msg + "】");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "javadong.topic", type = "topic"),
            key = "*.news"
    ))
    public void listenTopicQueue2(String msg) {
        System.out.println("消费者接收到的topic.queue2的消息：【" + msg + "】");
    }
}
