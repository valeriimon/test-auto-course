package ua.edu.duan.book_shop.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.enums.OrderState;
import ua.edu.duan.book_shop.repository.WarehouseRepository;

@Component
@RequiredArgsConstructor
public class NewOrderStateProcessor implements BaseOrderStateProcessor {

    private final WarehouseRepository warehouseRepository;

    @Override
    public OrderState getOrderState() {
        return OrderState.NEW;
    }

    @Override
    public OrderState processOrder(OrderEntity order) {
        warehouseRepository.findById(order.getItemId()).ifPresent(
                (item) -> {
                    if (item.getCount() > 0) {
                        order.setState(OrderState.PROCESSING);
                    } else {
                        order.setState(OrderState.CANCELED);
                    }
                }
        );
        return order.getState();
    }
}
