package nl.rug.aoop.application.trader;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.trader.command.TraderCommandHandlerFactory;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TraderMessageHandler {
    private final Map<String, Object> params;
    private CommandHandler commandHandler;
    private TraderCommandHandlerFactory commandHandlerFactory;

    public TraderMessageHandler(TraderData traderData) {
        this.commandHandlerFactory = new TraderCommandHandlerFactory(traderData);
        this.commandHandler = this.commandHandlerFactory.createTraderCommandHandler();
        this.params = new HashMap<>();
    }

    public void handleMessage(Message message) {
        if (message == null) {
            log.error("Null message");
        } else {
            String command = message.getHeader();
            String info = message.getBody();

            this.params.put("command", command);
            this.params.put("info", info);

            this.commandHandler.execute(command, params);
        }
    }
}
