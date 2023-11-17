package nl.rug.aoop.application.stockExchange;

import nl.rug.aoop.application.stockExchange.command.BuyOrderCommand;
import nl.rug.aoop.application.stockExchange.command.SellOrderCommand;
import nl.rug.aoop.command.CommandHandler;

public class OrderCommandHandlerFactory {
    private StockExchangeData stockExchangeData;

    public OrderCommandHandlerFactory(StockExchangeData stockExchangeData) {
        this.stockExchangeData = stockExchangeData;
    }

    public CommandHandler createStockExchangeCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("BuyOrder", new BuyOrderCommand(this.stockExchangeData));
        commandHandler.registerCommand("SellOrder", new SellOrderCommand(this.stockExchangeData));
        return commandHandler;
    }
}
