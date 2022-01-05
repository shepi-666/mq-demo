package cn.itcast.mq.springamqp;

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
}
