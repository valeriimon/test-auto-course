package ua.edu.duan.book_shop.state;

import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.enums.OrderState;

public interface BaseOrderStateProcessor {
    OrderState getOrderState();

    OrderState processOrder(OrderEntity order);
}
