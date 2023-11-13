package nl.rug.aoop.messagequeue.consumer;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;

@Slf4j
public class NetworkConsumer implements MQConsumer {
    Client client;

    public NetworkConsumer(Client client) {this.client = client;}

    public String createPollMessage() {
        return new NetworkMessage("MqPoll", null, null).toJson();
    }

    @Override
    public Message poll() {
        String jsonMessage = this.createPollMessage();
        log.info(jsonMessage);
        client.sendMessage(jsonMessage);
        return null;
    }
}
