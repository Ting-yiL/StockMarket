package nl.rug.aoop.networking.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.messagequeue.Communicator;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MQServerMessageHandler implements MessageHandler{
    private final Map<String, Object> params;
    private final ThreadSafeMessageQueue queue;
    private final CommandHandler commandHandler;
    @Getter
    private Map<Integer, ClientHandler> clientHandlers = new HashMap<>();

    public MQServerMessageHandler(ThreadSafeMessageQueue queue) {
        this.params = new HashMap<>();
        this.queue = queue;
        this.commandHandler = new MQServerCommandHandlerFactory(this.queue).createMQCommandHandler();
    }

    public synchronized void handleMessage(String jsonMessage, int clientHandlerId) {
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

            this.params.put("reference", clientHandlers.get(clientHandlerId));

            this.commandHandler.execute(command, params);
        }
    }

    public synchronized void handleMessage(String jsonMessage) {
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

            this.commandHandler.execute(command, params);
        }
    }
}
