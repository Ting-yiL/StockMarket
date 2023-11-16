package nl.rug.aoop.networking.messagequeue;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import nl.rug.aoop.messagequeue.message.Message;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class NetworkMedium implements Medium {
    AtomicBoolean received = new AtomicBoolean(false);
    @Getter
    Message message;

    public NetworkMedium() {}

    public Message retrieveMessage() {
        while (!received.get()) {
            Thread.onSpinWait();
        }
        log.info("Retrieve message: " + message.toJson());
        return this.message;
    }

    public void receiveMessage(Message message) {
        this.resetStatus();
        if (message != null) {
            log.info("Receive message" + message.toJson());
            this.message = message;
            this.received.set(true);
        }
    }

    public void resetStatus() {
        received.set(false);
        message = null;
    }
}
