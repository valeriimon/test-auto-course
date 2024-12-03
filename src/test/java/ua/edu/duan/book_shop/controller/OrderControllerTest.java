package ua.edu.duan.book_shop.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ua.edu.duan.book_shop.dto.CreateOrderDto;
import ua.edu.duan.book_shop.dto.OrderDto;
import ua.edu.duan.book_shop.service.OrderService;

import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Test
    public void testCreateOrderShouldCallServiceMethod() {
        CreateOrderDto createOrderDto = new CreateOrderDto();
        when(orderService.createOrder(createOrderDto)).thenReturn(new OrderDto());

        orderController.createOrder(createOrderDto);

        Mockito.verify(orderService, Mockito.times(1)).createOrder(createOrderDto);
    }

    @Test
    public void testProcessOrderShouldCallServiceMethod() {
        when(orderService.processOrder(1)).thenReturn(new OrderDto());

        orderController.processOrder(1);

        Mockito.verify(orderService, Mockito.times(1)).processOrder(1);
    }
}
