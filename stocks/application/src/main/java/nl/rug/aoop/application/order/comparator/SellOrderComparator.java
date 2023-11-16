package nl.rug.aoop.application.order.comparator;

import nl.rug.aoop.application.order.LimitOrder;

public class SellOrderComparator extends OrderComparator{
    @Override
    public int compare(LimitOrder order1, LimitOrder order2) {
        if (order1.getPrice() < order2.getPrice()) {
            return -1;
        } else if (order1.getPrice() > order2.getPrice()) {
            return 1;
        } else {
            return Integer.compare(order1.getQuantity(), order2.getQuantity());
        }
    }
}
