package nl.rug.aoop.networking.command;

import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.messagequeue.handler.MQServerCommandHandlerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestQueueCommandHandlerFactory {
    private ThreadSafeMessageQueue queue;
    private MQServerCommandHandlerFactory factory;

    @BeforeEach
    public void SetUp() {
        queue = new ThreadSafeMessageQueue();
        factory = new MQServerCommandHandlerFactory(queue);
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
