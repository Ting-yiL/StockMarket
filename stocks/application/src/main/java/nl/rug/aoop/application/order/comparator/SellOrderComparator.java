package nl.rug.aoop.application.order.comparator;

import nl.rug.aoop.application.order.LimitOrder;

/**
 * The Buy order comparator.
 */
public class SellOrderComparator extends OrderComparator{

    /**
     * Compare two sellOrders.
     * @param order1 the first object to be compared.
     * @param order2 the second object to be compared.
     * @return Favor the order that has lower price.
     */
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
