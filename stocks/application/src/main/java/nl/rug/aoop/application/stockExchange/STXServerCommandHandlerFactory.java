package nl.rug.aoop.application.stockExchange;

import nl.rug.aoop.application.stockExchange.StockExchangeData;
import nl.rug.aoop.application.stockExchange.command.RequestProfileCommand;
import nl.rug.aoop.application.stockExchange.command.RequestStockMapCommand;
import nl.rug.aoop.command.CommandHandler;
import nl.rug.aoop.messagequeue.queue.ThreadSafeMessageQueue;
import nl.rug.aoop.networking.command.MQPutCommand;
import nl.rug.aoop.networking.server.ClientHandler;

import java.util.HashMap;
import java.util.Map;

public class STXServerCommandHandlerFactory {
    private final ThreadSafeMessageQueue queue;
    private final StockExchangeData stockExchangeData;
    private final Map<Integer, ClientHandler> clientHandlers;

    public STXServerCommandHandlerFactory(ThreadSafeMessageQueue queue, StockExchangeData stockExchangeData) {
        this.queue = queue;
        this.stockExchangeData = stockExchangeData;
        this.clientHandlers = new HashMap<>();
    }

    public CommandHandler createSXServerCommandHandler() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand("MqPut", new MQPutCommand(this.queue));
        commandHandler.registerCommand("RequestProfile", new RequestProfileCommand());
        commandHandler.registerCommand("RequestStockMap",new RequestStockMapCommand());
        return commandHandler;
    }
}
