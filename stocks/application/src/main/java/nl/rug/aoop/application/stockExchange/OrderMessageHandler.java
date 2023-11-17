package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.OrderHandler;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OrderMessageHandler implements OrderHandler {
    private final Map<String, Object> params;
    private CommandHandler commandHandler;
    private OrderCommandHandlerFactory commandHandlerFactory;

    public OrderMessageHandler(StockExchangeData stockExchangeData) {
        this.commandHandlerFactory = new OrderCommandHandlerFactory(stockExchangeData);
        this.commandHandler = this.commandHandlerFactory.createStockExchangeCommandHandler();
        this.params = new HashMap<>();
    }

    @Override
    public void handleOrder(Message message) {
        if (message == null) {
            log.error("Null message");
        } else {
            String command = message.getHeader();
            String info = message.getBody();

            this.params.put("command", command);
            this.params.put("info", info);

            log.info(command);
            log.info(info);

            this.commandHandler.execute(command, params);
        }
    }

}
