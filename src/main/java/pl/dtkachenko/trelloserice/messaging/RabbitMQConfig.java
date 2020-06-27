package pl.dtkachenko.trelloserice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class RabbitMQConfig {
    @Value("${rabbitmq.queueName}")
    public String QUEUE_NAME = "trello-queue";
    @Value("${rabbitmq.routingKey}")
    public String ROUTING_KEY = "trello-key";
    @Value("${rabbitmq.exchangeName}")
    public String EXCHANGE_NAME;

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new JavaTimeModule())
                .dateFormat(new StdDateFormat())
                .build();

        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
