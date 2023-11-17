package nl.rug.aoop.application;


import nl.rug.aoop.application.trader.TraderBotFacade;

import java.io.IOException;
import java.nio.file.Path;

public class BotApplication {
    private static final int TIMEOUT = 5000;
    private final int port = 6200;
    private final Path TRADERPATH = Path.of("stocks","data", "traders.yaml");
    private final Path STOCKPATH = Path.of("stocks","data", "stocks.yaml");
    private TraderBotFacade traderBotFacade;

    public static void main(String[] args) throws IOException, InterruptedException {
        BotApplication app = new BotApplication();
        app.initialize();
        app.run();
    }

    public void initialize() throws IOException {
        this.traderBotFacade = new TraderBotFacade(port, STOCKPATH, TRADERPATH);
        this.traderBotFacade.createBotsConnection();
    }

    public void run() throws InterruptedException {
        this.traderBotFacade.startTrading();
    }
}
