package nl.rug.aoop.networking.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MQServerMessageHandler implements MessageHandlerWithReference{
    private final Map<String, Object> params;
    private final ThreadSafeMessageQueue queue;
    private final CommandHandler commandHandler;
    @Getter
    private final Map<Integer, ClientHandler> clientHandlers = new HashMap<>();

    public MQServerMessageHandler(ThreadSafeMessageQueue queue) {
        this.params = new HashMap<>();
        this.queue = queue;
        this.commandHandler = new MQServerCommandHandlerFactory(this.queue, this.clientHandlers).createMQCommandHandler();
    }

    public synchronized void handleMessage(String jsonMessage) {
        NetworkMessage networkMessage = null;
        if (jsonMessage != null) {
            log.info("Handling message..");
            try {
                log.info("Decrypting Json to Network message");
                networkMessage = NetworkMessage.fromJson(jsonMessage);
            } catch (IllegalStateException ignored) {
                log.error("Error parsing Message", ignored);
            }
            log.info("Decrypt message..");
            if (networkMessage == null) {
                log.info("Note network message");
                log.info("Receiving: " + jsonMessage);
            } else {
                String command = networkMessage.getCommand();
                log.info("Command: " + command);

                String header = networkMessage.getHeader();
                log.info("Header: " + header);
                this.params.put("header", header);

                String body = networkMessage.getBody();
                log.info("Body: " + body);
                this.params.put("body", body);

                this.commandHandler.execute(command, params);
            }

        }
    }

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
