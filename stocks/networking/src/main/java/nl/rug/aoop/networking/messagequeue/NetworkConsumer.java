package nl.rug.aoop.networking.messagequeue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.consumer.MQConsumer;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.NetworkConsumerMessageHandler;

import java.io.IOException;
import java.net.InetSocketAddress;


@Slf4j
public class NetworkConsumer implements MQConsumer {
    @Getter
    private final Client client;
    private final Medium medium = new NetworkMedium();

    public NetworkConsumer(int port) throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        this.client = new Client(address, new NetworkConsumerMessageHandler(this.medium));;
    }

    public void start() {
        new Thread(client).start();
    }

    public void stop() {
        client.terminate();
    }

    public String createPollMessage() {
        return new NetworkMessage("MqPoll", null, null).toJson();
    }

    @Override
    public Message poll() {
        log.info("Retrieving message from medium");
        String pollMessage = createPollMessage();
        client.sendMessage(pollMessage);
        return this.medium.retrieveMessage();
    }
}
