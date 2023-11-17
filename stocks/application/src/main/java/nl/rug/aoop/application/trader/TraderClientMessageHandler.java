package nl.rug.aoop.application.trader;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.trader.command.TraderClientCommandHandlerFactory;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.handler.MessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * The TraderClientMessageHandler.
 */
@Slf4j
public class TraderClientMessageHandler implements MessageHandler {
    private final Map<String, Object> params;
    private CommandHandler commandHandler;
    private TraderClientCommandHandlerFactory commandHandlerFactory;

    /**
     * The constructor of TraderClientMessageHandler.
     * @param traderClient The traderClient.
     */
    public TraderClientMessageHandler(TraderClient traderClient) {
        this.commandHandlerFactory = new TraderClientCommandHandlerFactory(traderClient);
        this.commandHandler = this.commandHandlerFactory.createTraderClientCommandHandler();
        this.params = new HashMap<>();
    }

    /**
     * Handling the message.
     * @param jsonMessage The incoming message.
     */
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
