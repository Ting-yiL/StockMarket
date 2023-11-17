package nl.rug.aoop.application.stockExchange;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.application.order.OrderHandler;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * The Order MessageHandler.
 */
@Slf4j
public class OrderMessageHandler implements OrderHandler {
    private final Map<String, Object> params;
    private CommandHandler commandHandler;
    private OrderCommandHandlerFactory commandHandlerFactory;

    /**
     * The constructor of OrderMessageHandler.
     * @param stockExchangeData The StockExchangeData.
     * @param stxManager The STX Manager.
     */
    public OrderMessageHandler(StockExchangeData stockExchangeData, STXManager stxManager) {
        this.commandHandlerFactory = new OrderCommandHandlerFactory(stockExchangeData, stxManager);
        this.commandHandler = this.commandHandlerFactory.createStockExchangeCommandHandler();
        this.params = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     * @param message The message.
     */
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
