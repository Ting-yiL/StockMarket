package nl.rug.aoop.application.trader.command;

import nl.rug.aoop.application.trader.TraderData;
import nl.rug.aoop.command.CommandHandler;

public class TraderCommandHandlerFactory {
    private TraderData traderData;

    public TraderCommandHandlerFactory(TraderData traderData) {
        this.traderData = traderData;
    }

    public CommandHandler createTraderCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        return commandHandler;
    }
}
