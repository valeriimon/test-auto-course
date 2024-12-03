package ua.edu.duan.book_shop.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ua.edu.duan.book_shop.dto.CreateOrderDto;
import ua.edu.duan.book_shop.dto.OrderDto;
import ua.edu.duan.book_shop.entity.OrderEntity;
import ua.edu.duan.book_shop.entity.WarehouseEntity;
import ua.edu.duan.book_shop.enums.OrderState;
import ua.edu.duan.book_shop.repository.OrderRepository;
import ua.edu.duan.book_shop.repository.WarehouseRepository;
import ua.edu.duan.book_shop.state.BaseOrderStateProcessor;
import ua.edu.duan.book_shop.state.BookOrderStateProcessorProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;
    private final BookOrderStateProcessorProvider bookOrderStateProcessorProvider;

    public OrderDto createOrder(CreateOrderDto createOrderDto) {
        Optional<WarehouseEntity> item = warehouseRepository.findById(createOrderDto.getItemId());
        if (item.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Book was not found"
            );
        }

        OrderEntity order = new OrderEntity();
        order.setItemId(createOrderDto.getItemId());
        order.setState(OrderState.NEW);
        orderRepository.save(order);
        return convert(order);
    }

    @Transactional()
    public OrderDto processOrder(long orderId) {
        Optional<OrderEntity> orderResult = orderRepository.findById(orderId);
        if (orderResult.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Order was not found"
            );
        }

        OrderEntity order = orderResult.get();
        try {
            BaseOrderStateProcessor processor = bookOrderStateProcessorProvider.getStateProcessor(order.getState());
            processor.processOrder(order);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ex.getMessage()
            );
        }

        return convert(order);
    }

    private OrderDto convert(OrderEntity orderEntity) {
        OrderDto dto = new OrderDto();
        dto.setId(orderEntity.getId());
        dto.setItemId(orderEntity.getItemId());
        dto.setState(orderEntity.getState());
        return dto;
    }

    private OrderEntity convert(OrderDto dto) {
        OrderEntity entity = new OrderEntity();
        entity.setId(dto.getId());
        entity.setItemId(dto.getItemId());
        entity.setState(dto.getState());
        return entity;
    }
}
