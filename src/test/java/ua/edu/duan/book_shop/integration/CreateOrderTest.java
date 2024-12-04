package ua.edu.duan.book_shop.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import ua.edu.duan.book_shop.dto.CreateOrderDto;
import ua.edu.duan.book_shop.dto.OrderDto;
import ua.edu.duan.book_shop.enums.OrderState;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.edu.duan.book_shop.Utils.getExceptionReason;
import static ua.edu.duan.book_shop.Utils.stringifyJson;

@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Sql("/setup-for-create-order.sql")
public class CreateOrderTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOrderCreateShouldCreateSuccessfully() throws Exception {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setItemId(1);
        RequestBuilder createOrderRequest = post("/book-order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(createOrderDto));

        OrderDto expectedOrderDto = new OrderDto();
        expectedOrderDto.setId(1);
        expectedOrderDto.setItemId(1);
        expectedOrderDto.setState(OrderState.NEW);
        mockMvc.perform(createOrderRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(stringifyJson(expectedOrderDto)));

        List<OrderDto> orders = requestOrders();
        assertEquals(OrderState.NEW, orders.get(0).getState());
    }

    @Test
    public void testOrderCreateShouldFailWarehouseItemNotFound() throws Exception {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setItemId(1000);
        RequestBuilder createOrderRequest = post("/book-order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringifyJson(createOrderDto));

        mockMvc.perform(createOrderRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertEquals("Book was not found", getExceptionReason(result));
                });

        List<OrderDto> orders = requestOrders();
        assertEquals(0, orders.size());
    }

    private List<OrderDto> requestOrders() throws Exception {
        RequestBuilder getOrdersRequest = get("/book-order/get-all");
        String res = mockMvc.perform(getOrdersRequest).andReturn().getResponse().getContentAsString();
        return new ObjectMapper().readValue(res, new TypeReference<List<OrderDto>>() {});
    }
}
