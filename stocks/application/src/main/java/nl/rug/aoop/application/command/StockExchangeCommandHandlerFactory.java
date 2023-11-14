package nl.rug.aoop.application.command;

import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.command.CommandHandler;

public class StockExchangeCommandHandlerFactory {
    private StockExchangeData stockExchangeData;

    public StockExchangeCommandHandlerFactory(StockExchangeData stockExchangeData) {
        this.stockExchangeData = stockExchangeData;
    }

    public CommandHandler createStockExchangeCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("BuyOrder", new BuyOrderCommand(this.stockExchangeData));
        commandHandler.registerCommand("SellOrder", new SellOrderCommand(this.stockExchangeData));
        return commandHandler;
    }
}
