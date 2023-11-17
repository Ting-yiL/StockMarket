package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.handler.MessageHandler;
import nl.rug.aoop.networking.handler.MessageHandlerWithReference;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class STXServerMessageHandler implements MessageHandlerWithReference {
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
