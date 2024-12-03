package ua.edu.duan.book_shop.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ua.edu.duan.book_shop.dto.CreateOrderDto;
import ua.edu.duan.book_shop.dto.OrderDto;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.entity.WarehouseEntity;
import ua.edu.duan.book_shop.enums.OrderState;
import ua.edu.duan.book_shop.repository.OrderRepository;
import ua.edu.duan.book_shop.repository.WarehouseRepository;
import ua.edu.duan.book_shop.state.BookOrderStateProcessorProvider;
import ua.edu.duan.book_shop.state.NewOrderStateProcessor;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Mock
    private BookOrderStateProcessorProvider bookOrderStateProcessorProvider;

    @Mock
    private NewOrderStateProcessor newOrderStateProcessor;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testCreateOrder() {
        when(warehouseRepository.findById(any())).thenReturn(Optional.of(new WarehouseEntity()));

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setItemId(1);
        OrderDto orderDto = orderService.createOrder(createOrderDto);

        Mockito.verify(orderRepository, Mockito.times(1)).save(any(OrderEntity.class));
        assertEquals(OrderState.NEW, orderDto.getState());
    }

    @Test
    public void testCreateOrderWarehouseItemNotFoundException() {
        when(warehouseRepository.findById(any())).thenReturn(Optional.empty());

        CreateOrderDto createOrderDto = new CreateOrderDto();
        createOrderDto.setItemId(1);
        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> orderService.createOrder(createOrderDto)
        );

        assertEquals(thrown.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testProcessOrder() {
        when(orderRepository.findById(any())).thenReturn(Optional.of(new OrderEntity()));
        when(bookOrderStateProcessorProvider.getStateProcessor(any())).thenReturn(newOrderStateProcessor);
        when(newOrderStateProcessor.processOrder(any())).thenReturn(OrderState.NEW);

        orderService.processOrder(1);

        Mockito.verify(
                bookOrderStateProcessorProvider,
                Mockito.times(1)
        ).getStateProcessor(any());
        Mockito.verify(
                newOrderStateProcessor,
                Mockito.times(1)
        ).processOrder(any());
    }

    @Test
    public void testProcessOrderOrderNotFoundException() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(
                ResponseStatusException.class,
                () -> orderService.processOrder(1)
        );

        assertEquals(thrown.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
