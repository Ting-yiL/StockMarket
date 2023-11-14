package nl.rug.aoop.networking.messagequeue;

import lombok.Getter;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.concurrent.atomic.AtomicBoolean;

public class NetworkCommunicator implements Communicator {
    AtomicBoolean received = new AtomicBoolean(false);
    @Getter
    Message message;

    public NetworkCommunicator() {}

    public void receiveMessage(Message message) {
        if (message != null) {
            this.message = message;
            this.received.set(true);
        }
    }

    public Boolean getStatus() {
        return received.get();
    }

    public void resetStatus() {
        received.set(false);
        message = null;
    }
}
