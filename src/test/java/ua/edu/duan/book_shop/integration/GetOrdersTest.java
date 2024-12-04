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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Sql("/setup-for-get-orders.sql")
public class GetOrdersTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetOrdersShouldReturnListOfRecords() throws Exception {
        RequestBuilder getOrdersRequest = get("/book-order/get-all");

        mockMvc.perform(getOrdersRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].state").value(OrderState.NEW.toString()))
                .andExpect(jsonPath("[1].state").value(OrderState.PROCESSING.toString()))
                .andExpect(jsonPath("[2].state").value(OrderState.DELIVERED.toString()))
                .andExpect(jsonPath("[3].state").value(OrderState.CANCELED.toString()));
    }
}
