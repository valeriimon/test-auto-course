package ua.edu.duan.book_shop.state;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.entity.WarehouseEntity;
import ua.edu.duan.book_shop.enums.OrderState;
import ua.edu.duan.book_shop.repository.WarehouseRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NewOrderStateProcessorTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private NewOrderStateProcessor newOrderStateProcessor;

    @Test
    public void testGetOrderState() {
        OrderState state = newOrderStateProcessor.getOrderState();

        assertEquals(OrderState.NEW, state);
    }

    @Test
    public void testProcessOrderShouldReturnCorrectStateIfItemCountGreaterThanZero() {
        WarehouseEntity item = new WarehouseEntity();
        item.setCount(20);
        when(warehouseRepository.findById(any())).thenReturn(Optional.of(item));

        OrderState state = newOrderStateProcessor.processOrder(new OrderEntity());

        assertEquals(OrderState.PROCESSING, state);
    }

    @Test
    public void testProcessOrderShouldReturnCorrectStateIfItemCountEqualsZero() {
        WarehouseEntity item = new WarehouseEntity();
        item.setCount(0);
        when(warehouseRepository.findById(any())).thenReturn(Optional.of(item));

        OrderState state = newOrderStateProcessor.processOrder(new OrderEntity());

        assertEquals(OrderState.CANCELED, state);
    }
}
