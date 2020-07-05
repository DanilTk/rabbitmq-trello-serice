package pl.dtkachenko.trelloserice.messaging;

import org.hamcrest.core.Is;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import pl.dtkachenko.trelloserice.model.Task;

import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = RabbitMQPublisherTest.Initializer.class)
class RabbitMQPublisherTest {
    @ClassRule
    public static GenericContainer rabbit = new GenericContainer("rabbitmq:3.6-management-alpine").withExposedPorts(5672, 15672);

    @Rule
    public OutputCaptureRule outputCapture = new OutputCaptureRule();

    @Autowired
    RabbitMQPublisher publisher;

    @Test
    void publishToQueue() {
        Task task = new Task("Task", "Description", LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now());
        publisher.publishToQueue(task);
        await().atMost(5, TimeUnit.SECONDS).until(isMessageConsumed(), Is.is(true));
    }

    private Callable<Boolean> isMessageConsumed() {
        return () -> outputCapture.toString().contains("Task");
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues values = TestPropertyValues.of(
                    "spring.rabbitmq.host=" + rabbit.getContainerId(),
                    "spring.rabbitmq.port=" + rabbit.getMappedPort(5672));
            values.applyTo(configurableApplicationContext);
        }
    }
}