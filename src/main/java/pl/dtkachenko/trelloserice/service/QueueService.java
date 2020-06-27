package pl.dtkachenko.trelloserice.service;

import org.springframework.stereotype.Service;
import pl.dtkachenko.trelloserice.messaging.RabbitMQPublisher;
import pl.dtkachenko.trelloserice.model.Task;

import java.util.Set;

@Service
public class QueueService {
    private RabbitMQPublisher publisher;
    private TrelloService trelloService;

    public QueueService(RabbitMQPublisher publisher, TrelloService trelloService) {
        this.publisher = publisher;
        this.trelloService = trelloService;
    }

    public void publishTasks() {
        Set<Task> tasks = trelloService.retrieveTasks();
        tasks.forEach(task -> publisher.publishToQueue(task));
    }
}
