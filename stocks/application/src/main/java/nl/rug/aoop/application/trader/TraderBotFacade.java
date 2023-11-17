package nl.rug.aoop.application.trader;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The TraderBot Facade.
 */
public class TraderBotFacade {
    private List<TraderData> traders;
    private List<TraderBot> traderBotList = new ArrayList<>() {
    };
    private int port;

    /**
     * The constructor of TraderBotFacade.
     * @param port The port.
     * @param traderPath The path to Traders data.
     * @throws IOException IOException.
     */
    public TraderBotFacade(int port, Path traderPath) throws IOException {
        this.port = port;
        YamlLoader yamlLoader = new YamlLoader(traderPath);
        traders = yamlLoader.load(new TypeReference<>() {});
    }

    /**
     * Creating bots' connection to server.
     * @throws IOException IOException.
     */
    public void createBotsConnection() throws IOException {
        for (TraderData trader : traders) {
            TraderClient traderClientBuffer = new TraderClient(this.port, trader.getId());
            traderBotList.add(new TraderBot(traderClientBuffer));
        }
    }

    /**
     * Starting the bots to trade.
     * @throws InterruptedException InterruptedException.
     */
    public void startTrading() throws InterruptedException {
        for (TraderBot traderBot : this.traderBotList) {
            traderBot.trade();
        }
    }

    /**
     * Stopping the bots to trade.
     * @throws InterruptedException InterruptedException.
     */
    public void stopTrading() throws InterruptedException {
        for (TraderBot traderBot : this.traderBotList) {
            traderBot.terminate();
        }
    }
}
