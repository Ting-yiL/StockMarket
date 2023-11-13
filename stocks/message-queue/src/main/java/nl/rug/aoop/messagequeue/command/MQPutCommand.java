package nl.rug.aoop.messagequeue.command;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;

/**
 * MQPutCommand - A command to put message to the MessageQueue.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
public class MQPutCommand implements Command {
    private final ThreadSafeMessageQueue queue;

    /**
     * the Constructor of the MQPutCommand.
     * @param queue The queue that the message will be put into.
     */
    public MQPutCommand(ThreadSafeMessageQueue queue) {
        this.queue = queue;
    }

    /**
     * The execution of the command.
     * @param params parameter map is
     * that it consists of key-value pairs similar to JSON.
     */
    public void execute(Map<String, Object> params) {
        if (!params.isEmpty() && (params != null)) {
            if ((params.get("header") instanceof String) && (params.get("body") instanceof String)) {
                String header= (String) params.get("header");
                String body = (String) params.get("body");
                this.queue.enqueue(new Message(header, body));
                log.info("Enqueue Successful, Header: " + header + ", Body: " + body);
                return;
            }
        }
        log.info("Enqueue Unsuccessful");
    }
}
