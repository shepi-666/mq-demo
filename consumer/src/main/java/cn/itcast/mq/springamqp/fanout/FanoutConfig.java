package cn.itcast.mq.springamqp.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    // 声明Fanout交换机
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange("javadong.fanout");
    }

    // 声明第一个队列
    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanout.queue1");
    }

    // 将第一个队列和交换机进行绑定
    @Bean
    public Binding bindingQueue1(Queue fanoutQueue1, FanoutExchange exchange) {
        return BindingBuilder.bind(fanoutQueue1).to(exchange);
    }

    // 声明第二个队列
    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanout.queue2");
    }

    // 将第一个队列和交换机进行绑定
    @Bean
    public Binding bindingQueue2(Queue fanoutQueue2, FanoutExchange exchange) {
        return BindingBuilder.bind(fanoutQueue2).to(exchange);
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("object.queue");
    }

}
