package nl.rug.aoop.networking.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.Command;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.Map;

@Slf4j
public class MQPollCommand implements Command {
    private final ThreadSafeMessageQueue queue;
    private final Map<Integer, ClientHandler> clientHandlerMap;

    public MQPollCommand(ThreadSafeMessageQueue queue, Map<Integer, ClientHandler> clientHandlerMap) {
        this.queue = queue;
        this.clientHandlerMap = clientHandlerMap;
    }

    public void execute(Map<String, Object> params) {
        if (!params.isEmpty() && params.containsKey("reference")) {
            String header= (String) params.get("header");
            String body = (String) params.get("body");
            Integer clientHandlerId = (Integer) params.get("reference");
            if (this.queue.getSize()<=0) {
                log.info("Empty Queue");
                Message message = new Message("EmptyQueue", "");
                ClientHandler clientHandler = this.clientHandlerMap.get(clientHandlerId);
                clientHandler.sendMessage(message.toJson());
                log.info("Message sent");
            } else if (!params.isEmpty() && params.containsKey("reference")) {
                Message message = this.queue.dequeue();
                log.info("Dequeue:" + message.toJson());
                ClientHandler clientHandler = this.clientHandlerMap.get(clientHandlerId);
                clientHandler.sendMessage(message.toJson());
                log.info("Message sent");
            } else {
                log.info("Dequeue Unsuccessful");
            }
        } else {
            log.info("Invalid params");
        }
    }
}
