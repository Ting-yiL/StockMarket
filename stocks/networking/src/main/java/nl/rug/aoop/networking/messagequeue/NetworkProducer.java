package nl.rug.aoop.networking.messagequeue;

import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.producer.MQProducer;
import nl.rug.aoop.networking.client.Client;

import java.io.IOException;

/**
 * NetworkProducer - The Producer of Message Queue, extended from Client.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Slf4j
public class NetworkProducer implements MQProducer {
    Client client;

    /**
     * The constructor of NetworkProducer
     * @param client the client that connects to the server.
     */
    public NetworkProducer(Client client) {
        this.client = client;
    }

    /**
     * Put command format of the message in JSON String.
     * @param message The Message.
     * @return The JSON String version of the Message.
     */
    public String createPutMessage(Message message) {
        return new NetworkMessage("MqPut", message.getHeader(), message.getBody()).toJson();
    }

    /**
     * Convert message to JSON String and send it to the server.
     * @param message The message you want to add to the queue.
     */
    @Override
    public void put(Message message) {
        String jsonMessage = this.createPutMessage(message);
        log.info(jsonMessage);
        client.sendMessage(jsonMessage);
    }
}
