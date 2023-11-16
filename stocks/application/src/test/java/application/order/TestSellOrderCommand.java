package application.order;

import nl.rug.aoop.application.order.SellOrder;
import nl.rug.aoop.application.order.comparator.SellOrderComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSellOrderCommand {
    private PriorityQueue<SellOrder> queue = new PriorityQueue<>(new SellOrderComparator());
    private SellOrder sellOrder1;
    private SellOrder sellOrder2;
    private SellOrder sellOrder3;

    @BeforeEach
    void SetUp() {
        this.sellOrder1 = new SellOrder("01", "ABC", 10, 10);
        this.sellOrder2 = new SellOrder("01", "ABC", 100, 10);
        this.sellOrder3 = new SellOrder("01", "ABC", 1000, 10);
    }

    @Test
    void queueOrdering() {
        this.queue.add(this.sellOrder2);
        this.queue.add(this.sellOrder1);
        this.queue.add(this.sellOrder3);

        assertEquals(this.sellOrder1, this.queue.poll());
        assertEquals(this.sellOrder2, this.queue.poll());
        assertEquals(this.sellOrder3, this.queue.poll());
    }
}
