package ua.edu.duan.book_shop.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.enums.OrderState;
import ua.edu.duan.book_shop.repository.WarehouseRepository;

@Component
@RequiredArgsConstructor
public class ProcessingOrderStateProcessor implements BaseOrderStateProcessor {

    private final WarehouseRepository warehouseRepository;

    @Override
    public OrderState getOrderState() {
        return OrderState.PROCESSING;
    }

    @Override
    public OrderState processOrder(OrderEntity order) {
        warehouseRepository.findById(order.getItemId())
                        .ifPresent(item -> {
                            item.setCount(item.getCount() - 1);
                            order.setState(OrderState.DELIVERED);
                        });

        return order.getState();
    }
}
