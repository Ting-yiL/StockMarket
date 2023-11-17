package nl.rug.aoop.application.trader.command;

import nl.rug.aoop.application.trader.TraderClient;
import nl.rug.aoop.command.CommandHandler;

/**
 * The TraderClientCommandHandlerFactory class.
 */
public class TraderClientCommandHandlerFactory {
    private TraderClient traderClient;

    /**
     * The constructor of TraderClientCommandHandlerFactory.
     * @param traderClient The traderClient.
     */
    public TraderClientCommandHandlerFactory(TraderClient traderClient) {
        this.traderClient = traderClient;
    }

    /**
     * Creating a TraderClientCommandHandler.
     * @return The createTraderClientCommandHandler.
     */
    public CommandHandler createTraderClientCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("UpdateProfile", new UpdatePortfolioCommand(this.traderClient));
        commandHandler.registerCommand("UpdateStockMap", new UpdateStockMapCommand(this.traderClient));
        return commandHandler;
    }
}
