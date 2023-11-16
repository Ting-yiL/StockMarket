package nl.rug.aoop.application.trader.command;

import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.command.CommandHandler;

public class TraderClientCommandHandlerFactory {
    private TraderClient traderClient;

    public TraderClientCommandHandlerFactory(TraderClient traderClient) {
        this.traderClient = traderClient;
    }

    public CommandHandler createTraderClientCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("UpdateProfile", new UpdatePortfolioCommand(this.traderClient));
        commandHandler.registerCommand("UpdateStockMap", new UpdateStockMapCommand(this.traderClient));
        return commandHandler;
    }
}
