package nl.rug.aoop.application.stockExchange;

import nl.rug.aoop.application.stockExchange.command.BuyOrderCommand;
import nl.rug.aoop.application.stockExchange.command.SellOrderCommand;
import nl.rug.aoop.command.CommandHandler;

/**
 * The OrderCommandHandlerFactory.
 */
public class OrderCommandHandlerFactory {
    private StockExchangeData stockExchangeData;
    private STXManager stxManager;

    /**
     * The constructor of a OrderCommandHandlerFactory.
     * @param stockExchangeData The StockExchangeData.
     * @param stxManager The STX Manager.
     */
    public OrderCommandHandlerFactory(StockExchangeData stockExchangeData, STXManager stxManager) {
        this.stockExchangeData = stockExchangeData;
        this.stxManager = stxManager;
    }

    /**
     * Create a StockExchangeCommandHandler.
     * @return The StockExchangeCommandHandler.
     */
    public CommandHandler createStockExchangeCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("BuyOrder", new BuyOrderCommand(this.stockExchangeData, this.stxManager));
        commandHandler.registerCommand("SellOrder", new SellOrderCommand(this.stockExchangeData, this.stxManager));
        return commandHandler;
    }
}
