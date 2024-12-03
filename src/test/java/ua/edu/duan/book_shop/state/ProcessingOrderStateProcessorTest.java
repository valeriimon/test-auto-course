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
public class ProcessingOrderStateProcessorTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private ProcessingOrderStateProcessor processingOrderStateProcessor;

    @Test
    public void testGetOrderState() {
        OrderState state = processingOrderStateProcessor.getOrderState();

        assertEquals(OrderState.PROCESSING, state);
    }

    @Test
    public void testProcessOrderShouldReturnCorrectState() {
        when(warehouseRepository.findById(any())).thenReturn(Optional.of(new WarehouseEntity()));

        OrderState state = processingOrderStateProcessor.processOrder(new OrderEntity());

        assertEquals(OrderState.DELIVERED, state);
    }

    @Test
    public void testProcessOrderShouldDecreaseItemCount() {
        WarehouseEntity item = new WarehouseEntity();
        item.setCount(20);
        when(warehouseRepository.findById(any())).thenReturn(Optional.of(item));

        processingOrderStateProcessor.processOrder(new OrderEntity());

        assertEquals(19, item.getCount());
    }

}
