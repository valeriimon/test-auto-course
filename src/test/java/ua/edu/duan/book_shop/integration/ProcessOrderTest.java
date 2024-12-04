package ua.edu.duan.book_shop.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import ua.edu.duan.book_shop.enums.OrderState;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.edu.duan.book_shop.Utils.getExceptionReason;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Sql(value = "/setup-for-processing-orders.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class ProcessOrderTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testProcessOrderShouldFailOrderIsNotFound() throws Exception {
        RequestBuilder request = getProcessOrderRequest(1000);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Order was not found", getExceptionReason(result)));
    }

    @Test
    public void testProcessOrderWithStateNewAndCountEqualsZero() throws Exception {
        RequestBuilder request = getProcessOrderRequest(1);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("state").value(OrderState.CANCELED.toString()));
    }

    @Test
    public void testProcessOrderWithStateNewAndCountGreaterThanZero() throws Exception {
        RequestBuilder request = getProcessOrderRequest(2);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("state").value(OrderState.PROCESSING.toString()));
    }

    @Test
    public void testProcessOrderWithStateProcessing() throws Exception {
        RequestBuilder request = getProcessOrderRequest(3);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("state").value(OrderState.DELIVERED.toString()));
    }

    @Test
    public void testProcessOrderWithStateDelivered() throws Exception {
        RequestBuilder request = getProcessOrderRequest(4);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Order has been already delivered", getExceptionReason(result)));
    }

    @Test
    public void testProcessOrderWithStateCanceled() throws Exception {
        RequestBuilder request = getProcessOrderRequest(5);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("Order has been cancelled", getExceptionReason(result)));
    }

    private RequestBuilder getProcessOrderRequest(long orderId) {
        return patch("/book-order/process/" + orderId);
    }
}
