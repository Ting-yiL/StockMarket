package nl.rug.aoop.application.trader;

import nl.rug.aoop.networking.messagequeue.NetworkProducer;

public class TraderClient {
    private TraderData traderData;
    private NetworkProducer networkProducer;
    private TraderMessageHandler messageHandler;

    public TraderClient(NetworkProducer networkProducer, TraderData traderData,
                        TraderMessageHandler messageHandler) {
        this.networkProducer = networkProducer;
        this.traderData = traderData;
        this.messageHandler = messageHandler;
    }
}
