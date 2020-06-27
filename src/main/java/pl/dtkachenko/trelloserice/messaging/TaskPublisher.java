package pl.dtkachenko.trelloserice.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pl.dtkachenko.trelloserice.model.Task;

@Component
@Slf4j
public class TaskPublisher {
    private RabbitTemplate rabbitTemplate;

    public TaskPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishToQueue(Task task) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY, task);
        log.info("Published task: " + task);
    }
}