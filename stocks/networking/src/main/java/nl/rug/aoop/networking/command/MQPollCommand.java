package nl.rug.aoop.networking.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.networking.messagequeue.Communicator;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;

import java.util.Map;

@Slf4j
public class MQPollCommand implements Command {
    private final ThreadSafeMessageQueue queue;

    public MQPollCommand(ThreadSafeMessageQueue queue) { this.queue = queue;}

    public void execute(Map<String, Object> params) {
        if (this.queue.getSize()<=0) {
            log.info("Empty Queue");
        } else if (!params.isEmpty() && params.containsKey("reference")) {
            Communicator poller = (Communicator) params.get("reference");
            poller.receiveMessage(this.queue.dequeue());
        } else {
            log.info("Dequeue Unsuccessful");
        }
    }
}
