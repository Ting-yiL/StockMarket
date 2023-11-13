package nl.rug.aoop.messagequeue.handler;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.command.MQProducerCommandHandlerFactory;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;

import java.util.HashMap;
import java.util.Map;

/**
 * MQMessageHandler - Handling incoming Json String of message and execute command.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
public class NetworkProducerMessageHandler {
    private final Map<String, Object> params;
    private final ThreadSafeMessageQueue queue;
    private final CommandHandler commandHandler;

    /**
     * The constructor of MQMessageHandler.
     * @param queue ThreadSafeMessageQueue in which operations occurs.
     */
    public NetworkProducerMessageHandler(ThreadSafeMessageQueue queue) {
        this.params = new HashMap<>();
        this.queue = queue;
        this.commandHandler = new MQProducerCommandHandlerFactory(this.queue).createMQCommandHandler();
    }

    /**
     * Handling Message and execute command.
     * @param jsonMessage Incoming message.
     */
    public void handleMessage(String jsonMessage, Object reference) {
        if (jsonMessage != null) {
            log.info("Handling message of Network Producer..");
            NetworkMessage networkMessage = NetworkMessage.fromJson(jsonMessage);
            String command = networkMessage.getCommand();
            log.info("Command: " + command);
            String header = networkMessage.getHeader();
            log.info("Header: " + header);
            String body = networkMessage.getBody();
            log.info("Body: " + body);

            this.params.put("header", header);
            this.params.put("body", body);
            this.params.put("reference", reference);

            this.commandHandler.execute(command, params);
        }
    }
}
