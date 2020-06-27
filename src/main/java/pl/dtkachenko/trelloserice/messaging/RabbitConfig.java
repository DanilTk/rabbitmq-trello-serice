package pl.dtkachenko.trelloserice.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class RabbitConfig {
    @Value("${rabbitmq.queueName}")
    public String QUEUE_NAME = "trello-queue";
    @Value("${rabbitmq.routingKey}")
    public String ROUTING_KEY = "trello-key";
    @Value("${rabbitmq.exchangeName}")
    public String EXCHANGE_NAME;

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerConverter());
        return rabbitTemplate;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding trelloBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    Jackson2JsonMessageConverter producerConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    MappingJackson2MessageConverter consumerConverter() {
        return new MappingJackson2MessageConverter();
    }
}
