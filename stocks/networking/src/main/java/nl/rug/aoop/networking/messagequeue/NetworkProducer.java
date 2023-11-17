package nl.rug.aoop.networking.messagequeue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.messagequeue.producer.MQProducer;
import nl.rug.aoop.networking.client.Client;
import nl.rug.aoop.networking.handler.MessageHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * NetworkProducer - The Producer of Message Queue, extended from Client.
 * @author Dylan Bonatz, Ting-Yi Lin
 * @version 1.0
 */
@Getter
@Slf4j
public class NetworkProducer implements MQProducer {
    private final Client client;
    private MessageHandler messageHandler;

    public NetworkProducer(int port, MessageHandler messageHandler) throws IOException {
        InetSocketAddress address = new InetSocketAddress(port);
        this.messageHandler = messageHandler;
        this.client = new Client(address, this.messageHandler);

    }

    public void start() {
        log.info("Start running the client");
        new Thread(client).start();
    }

    public void stop() {
        log.info("Terminating the client");
        client.terminate();
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
