package nl.rug.aoop.networking.handler;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.messagequeue.MQCommunicator;
import nl.rug.aoop.networking.messagequeue.handler.MQServerMessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

public class TestMQServerMessageHandler {
    private NetworkMessage putNetworkCommand;
    private NetworkMessage pollNetworkCommand;
    private String putJsonCommand;
    private String pollJsonCommand;
    private ThreadSafeMessageQueue queue;
    private MQServerMessageHandler messageHandler;
    private Message message;
    private MQCommunicator mqCommunicatorMock;

    @BeforeEach
    void SetUp() {
        message = new Message("Test Header", "Test Body");
        this.queue = new ThreadSafeMessageQueue();
        this.mqCommunicatorMock = Mockito.mock(MQCommunicator.class);
        this.messageHandler = new MQServerMessageHandler(this.queue, mqCommunicatorMock);
        this.putNetworkCommand = new NetworkMessage("MqPut", message);
        this.putJsonCommand = this.putNetworkCommand.toJson();
        this.pollNetworkCommand = new NetworkMessage("MqPoll", message);
        this.pollJsonCommand = this.pollNetworkCommand.toJson();
    }

    @Test
    void TestConstructor() {
        assertNotNull(this.messageHandler);
    }

    @Test
    void TestHandlePutMessage() {
        this.messageHandler.handleMessage(this.putJsonCommand);
        assertEquals("Test Header", this.queue.getHead().getHeader());
        assertEquals("Test Body", this.queue.getHead().getBody());
    }

    @Test
    void TestHandlePollMessage() {
        this.queue.enqueue(message);
        this.messageHandler.handleMessage(this.pollJsonCommand);
        verify(this.mqCommunicatorMock).receiveMessage(this.message);
    }

    @Test
    void TestHandleMixedMessages() {
        assertEquals(0, this.queue.getSize());
        this.messageHandler.handleMessage(this.putJsonCommand);
        assertEquals("Test Header", this.queue.getHead().getHeader());
        assertEquals("Test Body", this.queue.getHead().getBody());
        assertEquals(1, this.queue.getSize());
        this.messageHandler.handleMessage(this.pollJsonCommand);
        assertEquals(0, this.queue.getSize());
    }
}
