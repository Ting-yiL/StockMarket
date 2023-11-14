package application.order;

import nl.rug.aoop.application.order.BuyOrder;
import nl.rug.aoop.application.order.comparator.BuyOrderComparator;
import nl.rug.aoop.application.stock.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuyOrderComparator {
    private PriorityQueue<BuyOrder> queue = new PriorityQueue<>(new BuyOrderComparator());
    private BuyOrder buyOrder1;
    private BuyOrder buyOrder2;
    private BuyOrder buyOrder3;

    @BeforeEach
    void SetUp() {
        this.buyOrder1 = new BuyOrder("01", "ABC", 10, 10);
        this.buyOrder2 = new BuyOrder("01", "ABC", 100, 10);
        this.buyOrder3 = new BuyOrder("01", "ABC", 1000, 10);
    }

    @Test
    void queueOrdering() {
        this.queue.add(this.buyOrder2);
        this.queue.add(this.buyOrder1);
        this.queue.add(this.buyOrder3);

        assertEquals(this.buyOrder3, this.queue.poll());
        assertEquals(this.buyOrder2, this.queue.poll());
        assertEquals(this.buyOrder1, this.queue.poll());
    }
}
