package ua.edu.duan.book_shop.state.base_processor;

import ua.edu.duan.book_shop.enums.OrderState;
import ua.edu.duan.book_shop.state.BaseOrderStateProcessor;


public record ProcessorModel(OrderState state, Class<? extends BaseOrderStateProcessor> expectedClass) {
}
