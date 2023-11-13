package nl.rug.aoop.networking.command;

import nl.rug.aoop.networking.command.MQPollCommand;
import nl.rug.aoop.networking.messagequeue.MQCommunicator;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMQPollCommand {
    private ThreadSafeMessageQueue queue;
    private MQPollCommand command;
    private Map params;
    private final MQCommunicator mqCommunicatorMock = Mockito.mock(MQCommunicator.class);

    @BeforeEach
    void SetUp() {
        this.queue = new ThreadSafeMessageQueue();
        this.command = new MQPollCommand(this.queue);
        this.params = new HashMap<>();
        this.params.put("reference", this.mqCommunicatorMock);
        this.queue.enqueue(new Message("test", "test"));
    }

    @Test
    void TestPut() {
        assertEquals(1, this.queue.getSize());
        this.command.execute(this.params);
        assertEquals(0, this.queue.getSize());
    }
}
