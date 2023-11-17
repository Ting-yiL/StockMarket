package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.MessageHandlerWithReference;

import java.util.HashMap;
import java.util.Map;

/**
 * The STXServerMessageHandler class.
 */
@Slf4j
public class STXServerMessageHandler implements MessageHandlerWithReference {
    private final Map<String, Object> params;
    private final ThreadSafeMessageQueue queue;
    private final STXManager stxManager;
    private final CommandHandler commandHandler;

    /**
     * The constructor of STXServerMessageHandler.
     * @param queue The queue.
     * @param stxManager The stxManager.
     */
    public STXServerMessageHandler(ThreadSafeMessageQueue queue, STXManager stxManager) {
        this.params = new HashMap<>();
        this.queue = queue;
        this.stxManager = stxManager;
        commandHandler = new STXServerCommandHandlerFactory(this.queue,this.stxManager).createSTXServerCommandHandler();
    }

    /**
     * Handling the message with reference.
     * @param jsonMessage The message.
     * @param reference The reference to refer to.
     */
    public synchronized void handleMessage(String jsonMessage, Object reference) {
        if (jsonMessage != null) {
            log.info("Handling message..");
            NetworkMessage networkMessage = NetworkMessage.fromJson(jsonMessage);

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
