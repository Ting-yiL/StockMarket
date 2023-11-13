package nl.rug.aoop.messagequeue.command;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueueCommandHandlerFactory {
    private ThreadSafeMessageQueue queue;
    private MQProducerCommandHandlerFactory factory;

    @BeforeEach
    public void SetUp() {
        queue = new ThreadSafeMessageQueue();
        factory = new MQProducerCommandHandlerFactory(queue);
    }

    @Test
    public void testQueueCommandHandlerFactoryConstructor() {
        assertNotNull(factory);
    }

    @Test
    public void testCreateMQCommandHandler() {
        CommandHandler commandHandler = factory.createMQCommandHandler();
        assertTrue(commandHandler.containsKey("MqPut"));
        assertTrue(commandHandler.containsKey("MqPoll"));
    }
}
