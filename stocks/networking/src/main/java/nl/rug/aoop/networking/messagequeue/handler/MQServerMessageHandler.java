package nl.rug.aoop.networking.messagequeue.handler;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.MessageHandler;
import nl.rug.aoop.networking.messagequeue.Communicator;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MQServerMessageHandler implements MessageHandler{
    private final Map<String, Object> params;
    private final ThreadSafeMessageQueue queue;
    private final CommandHandler commandHandler;
    private final Communicator reference;

    public MQServerMessageHandler(ThreadSafeMessageQueue queue, Communicator reference) {
        this.params = new HashMap<>();
        this.queue = queue;
        this.commandHandler = new MQServerCommandHandlerFactory(this.queue).createMQCommandHandler();
        this.reference = reference;
    }

    public void handleMessage(String jsonMessage) {
        if (jsonMessage != null) {
            log.info("Handling message of Network Consumer..");
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
