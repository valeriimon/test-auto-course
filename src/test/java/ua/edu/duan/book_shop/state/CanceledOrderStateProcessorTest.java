package ua.edu.duan.book_shop.state;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.enums.OrderState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CanceledOrderStateProcessorTest {

    @InjectMocks
    private CanceledOrderStateProcessor canceledOrderStateProcessor;

    @Test
    public void testGetOrderState() {
        OrderState state = canceledOrderStateProcessor.getOrderState();

        assertEquals(OrderState.CANCELED, state);
    }

    @Test
    public void testProcessOrderShouldThrowException() {
        assertThrows(
                RuntimeException.class,
                () -> canceledOrderStateProcessor.processOrder(new OrderEntity())
        );
    }
}
