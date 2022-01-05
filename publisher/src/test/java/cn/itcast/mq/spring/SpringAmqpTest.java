package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.awt.windows.ThemeReader;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() {
        String queueName = "simple.queue";
        String message = "hello! spring amqp";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testSendMessage2WorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello! spring amqp _ ";
        for (int i = 1; i < 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }

    }

    @Test
    public void testSendFanoutExchange() {
        // 交换机名称
        String exchangeName = "javadong.fanout";
        // 发送的消息
        String message = "Hello, everyone";

        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "javadong.direct";
        // 发送的消息
        String message = "Hello, someone";

        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }

    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "javadong.topic";
        // 发送的消息
        String message = "javadong 现在已经进入到学习的白热化阶段";

        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
    }
}
