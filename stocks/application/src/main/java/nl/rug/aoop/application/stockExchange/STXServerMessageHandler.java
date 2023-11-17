package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.MessageHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class STXServerMessageHandler implements MessageHandler {
    private final Map<String, Object> params;
    private final ThreadSafeMessageQueue queue;
    private final StockExchangeData stockExchangeData;
    private final CommandHandler commandHandler;

    public STXServerMessageHandler(ThreadSafeMessageQueue queue, StockExchangeData stockExchangeData) {
        this.params = new HashMap<>();
        this.queue = queue;
        this.stockExchangeData = stockExchangeData;
        commandHandler = new STXServerCommandHandlerFactory(this.queue,this.stockExchangeData).createSXServerCommandHandler();
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

            String reference = String.valueOf(clientHandlerId);
            log.info("ClientHandler ID: " + clientHandlerId);
            this.params.put("reference", clientHandlerId);

            this.commandHandler.execute(command, params);
        }
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
}
