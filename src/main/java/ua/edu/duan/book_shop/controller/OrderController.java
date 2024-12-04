package ua.edu.duan.book_shop.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.edu.duan.book_shop.dto.CreateOrderDto;
import ua.edu.duan.book_shop.dto.OrderDto;
import ua.edu.duan.book_shop.service.OrderService;

import java.util.List;

@RestController
@RequestMapping(path = "/book-order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(path = "get-all")
    public List<OrderDto> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping(path = "/create")
    public OrderDto createOrder(@RequestBody() CreateOrderDto body) {
        return orderService.createOrder(body);
    }

    @PatchMapping(path = "/process/{orderId}")
    public OrderDto processOrder(@PathVariable("orderId") long orderId) {
        return orderService.processOrder(orderId);
    }

    @DeleteMapping(path = "/delete/{orderId}")
    public String deleteOrder(@PathVariable("orderId") long orderId) {
        return orderService.deleteOrder(orderId);
    }
}
