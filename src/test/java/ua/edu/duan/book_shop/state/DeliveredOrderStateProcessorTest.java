package ua.edu.duan.book_shop.state;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.enums.OrderState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DeliveredOrderStateProcessorTest {

    @InjectMocks
    private DeliveredOrderStateProcessor deliveredOrderStateProcessor;

    @Test
    public void testGetOrderState() {
        OrderState state = deliveredOrderStateProcessor.getOrderState();

        assertEquals(OrderState.DELIVERED, state);
    }

    @Test
    public void testProcessOrderStateShouldThrowException() {
        assertThrows(
                RuntimeException.class,
                () -> deliveredOrderStateProcessor.processOrder(new OrderEntity())
        );
    }
}
