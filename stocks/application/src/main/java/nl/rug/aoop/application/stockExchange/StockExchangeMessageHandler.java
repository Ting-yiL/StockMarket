package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.command.StockExchangeCommandHandlerFactory;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class StockExchangeMessageHandler {
    private final Map<String, Object> params;
    private CommandHandler commandHandler;
    private StockExchangeCommandHandlerFactory commandHandlerFactory;

    public StockExchangeMessageHandler(StockExchangeData stockExchangeData) {
        this.commandHandlerFactory = new StockExchangeCommandHandlerFactory(stockExchangeData);
        this.commandHandler = this.commandHandlerFactory.createStockExchangeCommandHandler();
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
