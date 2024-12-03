package ua.edu.duan.book_shop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long itemId;
}
