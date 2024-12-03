package ua.edu.duan.book_shop.state;

import org.springframework.stereotype.Component;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.enums.OrderState;

@Component
public class DeliveredOrderStateProcessor implements BaseOrderStateProcessor{
    @Override
    public OrderState getOrderState() {
        return OrderState.DELIVERED;
    }

    @Override
    public OrderState processOrder(OrderEntity order) {
        throw new RuntimeException("Order has been already delivered");
    }
}
