package nl.rug.aoop.application.trader;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.rug.aoop.networking.handler.MessageLogger;
import nl.rug.aoop.networking.messagequeue.NetworkProducer;
import nl.rug.aoop.util.YamlLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraderBotFacade {
    private List<TraderData> traders;
    private Map<String, TraderBot> traderBotMap = new HashMap<>();
    private int port;

    public TraderBotFacade(Path traderPath, int port) throws IOException {
        this.port = port;
        YamlLoader yamlLoader2 = new YamlLoader(traderPath);
        traders = yamlLoader2.load(new TypeReference<>() {});
    }

    public void createBotsConnection() throws IOException {
        for (TraderData trader : traders) {
            NetworkProducer networkProducerBuffer = new NetworkProducer(this.port, new MessageLogger());
            TraderClient traderClientBuffer = new TraderClient(trader.getId(), networkProducerBuffer);
            traderClientBuffer.setTraderData(trader);
            traderBotMap.put(trader.getId(), new TraderBot(traderClientBuffer));
        }
    }
}
