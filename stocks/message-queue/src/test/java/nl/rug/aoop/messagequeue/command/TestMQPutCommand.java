package nl.rug.aoop.messagequeue.command;

import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

public class TestMQPutCommand {
    private ThreadSafeMessageQueue queue;
    private MQPutCommand command;
    private Map params;

    @BeforeEach
    void SetUp() {
        this.queue = new ThreadSafeMessageQueue();
        this.command = new MQPutCommand(this.queue);
        this.params = new HashMap();
        this.params.put("Header", "Test Header");
        this.params.put("Body", "Test Body");

    }

    @Test
    void TestPut() {
        this.command.execute(this.params);
        assertEquals(this.queue.getHead().getHeader(), this.params.get("Header"));
    }
}
