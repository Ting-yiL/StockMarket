package nl.rug.aoop.networking.handler;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.messagequeue.Communicator;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MQServerMessageHandler implements MessageHandler{
    private final Map<String, Object> params;
    private final ThreadSafeMessageQueue queue;
    private final CommandHandler commandHandler;
    @Setter
    private ClientHandler reference = null;

    public MQServerMessageHandler(ThreadSafeMessageQueue queue) {
        this.params = new HashMap<>();
        this.queue = queue;
        this.commandHandler = new MQServerCommandHandlerFactory(this.queue).createMQCommandHandler();
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

            this.params.put("reference", reference);

            this.commandHandler.execute(command, params);
        }
    }
}
