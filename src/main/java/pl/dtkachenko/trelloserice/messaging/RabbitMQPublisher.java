package pl.dtkachenko.trelloserice.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.dtkachenko.trelloserice.model.Task;

@Component
@Slf4j
public class RabbitMQPublisher {
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.routingKey}")
    public String ROUTING_KEY = "trello-key";
    @Value("${rabbitmq.exchangeName}")
    public String EXCHANGE_NAME;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishToQueue(Task task) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, task);
        log.info("Published task: " + task);
    }
}