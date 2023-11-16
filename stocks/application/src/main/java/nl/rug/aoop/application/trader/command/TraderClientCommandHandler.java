package nl.rug.aoop.application.trader.command;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.handler.MessageHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TraderClientCommandHandler extends CommandHandler {
    private final Map<String, Object> params;
    private TraderClient traderClient;
    private final CommandHandler commandHandler;

    public TraderClientCommandHandler(TraderClient traderClient) {
        this.params = new HashMap<>();
        this.traderClient = traderClient;
        this.commandHandler = new TraderClientCommandHandlerFactory(this.traderClient).createTraderClientCommandHandler();
    }
    @Override
    public void handleMessage(String jsonMessage) {
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
        ;
        log.info("TraderClient ID: " + this.traderClient.getTraderId());
        this.params.put("reference", this.traderClient);

        this.commandHandler.execute(command, params);
    }
}
