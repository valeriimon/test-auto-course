package ua.edu.duan.book_shop.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.edu.duan.book_shop.enums.OrderState;

@Component
@RequiredArgsConstructor
public class BookOrderStateProcessorProvider {
    private final NewOrderStateProcessor newOrderStateProcessor;
    private final ProcessingOrderStateProcessor processingOrderStateProcessor;
    private final DeliveredOrderStateProcessor deliveredOrderStateProcessor;
    private final CanceledOrderStateProcessor canceledOrderStateProcessor;

    public BaseOrderStateProcessor getStateProcessor(OrderState state) {
        return switch (state) {
            case NEW -> newOrderStateProcessor;
            case PROCESSING -> processingOrderStateProcessor;
            case DELIVERED -> deliveredOrderStateProcessor;
            case CANCELED -> canceledOrderStateProcessor;
        };
    }
}
