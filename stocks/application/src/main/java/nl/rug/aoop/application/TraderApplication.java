package nl.rug.aoop.application;

import nl.rug.aoop.application.trader.TraderBotFacade;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The TraderApplication.
 */
public class TraderApplication {
    private static final int TIMEOUT = 5000;
    private final int port = 6200;
    private final Path TRADERPATH = Path.of("stocks","data", "traders.yaml");
    private TraderBotFacade traderBotFacade;

    /**
     * The main method.
     * @param args String args.
     * @throws IOException IOException.
     * @throws InterruptedException InterruptedException.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        TraderApplication app = new TraderApplication();
        app.initialize();
        app.run();
    }

    /**
     * Initializing the app.
     * @throws IOException IOException.
     */
    public void initialize() throws IOException {
        this.traderBotFacade = new TraderBotFacade(port, TRADERPATH);
        this.traderBotFacade.createBotsConnection();
    }

    /**
     * Running the app.
     * @throws InterruptedException InterruptedException.
     */
    public void run() throws InterruptedException {
        this.traderBotFacade.startTrading();
    }
}
