package pl.dtkachenko.trelloserice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueListener {
    private static final Logger log = LoggerFactory.getLogger(QueueListener.class);

    @RabbitListener(queues = MessagingApplication.QUEUE_NAME)
    public void receiveMessage(final Message message) {
        log.info("Received message as generic: {}", message.toString());
    }
}
