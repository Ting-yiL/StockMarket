package nl.rug.aoop.networking.command;

import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.command.MQPutCommand;
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
        this.params.put("header", "Test Header");
        this.params.put("body", "Test Body");

    }

    @Test
    void TestPut() {
        assertEquals(0, this.queue.getSize());
        this.command.execute(this.params);
        assertEquals(this.queue.getHead().getHeader(), this.params.get("header"));
        assertEquals(this.queue.getHead().getBody(), this.params.get("body"));
        assertEquals(1, this.queue.getSize());
    }
}
