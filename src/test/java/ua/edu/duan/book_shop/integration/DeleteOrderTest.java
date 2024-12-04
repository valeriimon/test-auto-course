package ua.edu.duan.book_shop.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Sql("/setup-for-delete-order.sql")
public class DeleteOrderTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDeleteOrderShouldSuccessfullyDelete() throws Exception {
        RequestBuilder deleteOrderRequest = delete("/book-order/delete/" + 1);

        mockMvc.perform(deleteOrderRequest)
                .andExpect(status().isOk());

        RequestBuilder getOrdersRequest = get("/book-order/get-all");

        mockMvc.perform(getOrdersRequest)
                .andExpect(content().json("[]"));

    }
}
