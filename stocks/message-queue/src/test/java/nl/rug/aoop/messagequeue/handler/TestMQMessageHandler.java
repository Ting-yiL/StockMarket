package nl.rug.aoop.messagequeue.handler;

import nl.rug.aoop.messagequeue.command.MQProducerCommandHandlerFactory;
import nl.rug.aoop.messagequeue.producer.NetworkProducer;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class TestMQMessageHandler {
    String jsonString;
    ThreadSafeMessageQueue queue;
    private final NetworkProducer networkProducer = Mockito.mock(NetworkProducer.class);
    private NetworkProducerMessageHandler networkProducerMessageHandler;

    @BeforeEach
    void SetUp(){
        this.queue = new ThreadSafeMessageQueue();
        this.jsonString = "{\"Command\":\"MqPut\",\"Header\":\"header\",\"Body\":\"body\",\"Time\":\"2023-10-11T17:02:51.010668400\"}";
        this.networkProducerMessageHandler = new NetworkProducerMessageHandler(this.queue);
    }

    @Test
    void TestConstructor() {
        assertNotNull(this.networkProducerMessageHandler);
    }

    @Test
    void TestHandleMessage() {
        this.networkProducerMessageHandler.handleMessage(this.jsonString, this.networkProducer);
        assertEquals("header", this.queue.getHead().getHeader());
        assertEquals("body", this.queue.getHead().getBody());
    }
}
