package nl.rug.aoop.networking.handler;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.server.ClientHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMQServerMessageHandler {
    private NetworkMessage putNetworkCommand;
    private String putJsonCommand;
    private ThreadSafeMessageQueue queue;
    private MQServerMessageHandler messageHandler;
    private Message message;
    private ClientHandler clientHandlerMock;
    private Map<Integer, ClientHandler> clientHanders = new HashMap<>();

    @BeforeEach
    void SetUp() {
        message = new Message("Test Header", "Test Body");
        this.queue = new ThreadSafeMessageQueue();
        this.clientHandlerMock = Mockito.mock(ClientHandler.class);
        System.out.println(this.clientHanders.size());
        this.messageHandler = new MQServerMessageHandler(this.queue);
        this.messageHandler.getClientHandlers().put(0, this.clientHandlerMock);
        System.out.println(this.messageHandler.getClientHandlers().size());
        this.putNetworkCommand = new NetworkMessage("MqPut", message);
        this.putJsonCommand = this.putNetworkCommand.toJson();
    }

    @Test
    void TestConstructor() {
        assertNotNull(this.messageHandler);
    }

    @Test
    void TestHandlePutMessage() {
        this.messageHandler.handleMessage(this.putJsonCommand, null);
        assertEquals("Test Header", this.queue.getHead().getHeader());
        assertEquals("Test Body", this.queue.getHead().getBody());
    }
}
