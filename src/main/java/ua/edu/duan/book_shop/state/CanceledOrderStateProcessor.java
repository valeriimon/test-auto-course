package ua.edu.duan.book_shop.state;

import org.springframework.stereotype.Component;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.enums.OrderState;

@Component
public class CanceledOrderStateProcessor implements BaseOrderStateProcessor{
    @Override
    public OrderState getOrderState() {
        return OrderState.CANCELED;
    }

    @Override
    public OrderState processOrder(OrderEntity order) {
        throw new RuntimeException("Order has been cancelled");
    }
}
