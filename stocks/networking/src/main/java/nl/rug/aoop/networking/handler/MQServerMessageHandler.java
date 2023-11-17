package nl.rug.aoop.networking.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * MQServerMessageHandler - A class that handle incoming message.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
public class MQServerMessageHandler implements MessageHandlerWithReference{
    private final Map<String, Object> params;
    private final ThreadSafeMessageQueue queue;
    private final CommandHandler commandHandler;
    @Getter
    private final Map<Integer, ClientHandler> clientHandlers = new HashMap<>();

    /**
     * The Constructor of MQServerMessageHandler.
     * @param queue A threadSafeQueue.
     */
    public MQServerMessageHandler(ThreadSafeMessageQueue queue) {
        this.params = new HashMap<>();
        this.queue = queue;
        this.commandHandler = new MQServerCommandHandlerFactory(this.queue).createMQCommandHandler();
    }

    /**
     * Handling the message.
     * @param message The message.
     * @param reference The reference to refer to.
     */
    @Override
    public void handleMessage(String message, Object reference) {
        if (message != null) {
            log.info("Handling message..");
            NetworkMessage networkMessage = NetworkMessage.fromJson(message);

            String command = networkMessage.getCommand();
            log.info("Command: " + command);

            String header = networkMessage.getHeader();
            log.info("Header: " + header);
            this.params.put("header", header);

            String body = networkMessage.getBody();
            log.info("Body: " + body);
            this.params.put("body", body);

            this.params.put("reference", reference);

            this.commandHandler.execute(command, params);
        }
    }
}
