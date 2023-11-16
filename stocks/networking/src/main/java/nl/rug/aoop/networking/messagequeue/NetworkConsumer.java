package nl.rug.aoop.networking.messagequeue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.consumer.MQConsumer;
import nl.rug.aoop.messagequeue.message.Message;
import nl.rug.aoop.messagequeue.message.NetworkMessage;
import nl.rug.aoop.networking.client.Client;

import java.io.IOException;

@Slf4j
public class NetworkConsumer implements MQConsumer {
    @Getter
    private final Client client;
    private NetworkCommunicator communicator;
    private final int TIMEOUT = 5000;


    public NetworkConsumer(Client client, NetworkCommunicator communicator) {
        this.client = client;
        this.communicator = communicator;
    }

    public String createPollMessage() {
        return new NetworkMessage("MqPoll", null, null).toJson();
    }

    @Override
    public Message poll() {
        try {
            String pollMessage = createPollMessage();
            client.sendMessage(pollMessage);
            String response = client.receiveMessage(TIMEOUT);
            if (response != null) {
                log.info(response);
                return null;
            } else {
                log.error("Timeout while waiting for response from the server");
            }
        } catch (IOException e) {
            log.error("Error while polling: " + e.getMessage());
        }

        return null;
    }
}
