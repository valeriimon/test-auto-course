package ua.edu.duan.book_shop.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ua.edu.duan.book_shop.dto.CreateOrderDto;
import ua.edu.duan.book_shop.dto.OrderDto;
import ua.edu.duan.book_shop.service.OrderService;

@RestController
@RequestMapping(path = "/book-order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(path = "/create")
    public OrderDto createOrder(@RequestBody() CreateOrderDto body) {
        return orderService.createOrder(body);
    }

    @PatchMapping(path = "/process/{orderId}")
    public OrderDto processOrder(@PathVariable("orderId") long orderId) {
        return orderService.processOrder(orderId);
    }
}
