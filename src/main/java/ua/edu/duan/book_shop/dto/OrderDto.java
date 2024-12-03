package ua.edu.duan.book_shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import ua.edu.duan.book_shop.enums.OrderState;

@Getter
@Setter
public class OrderDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long itemId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrderState state;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
}
