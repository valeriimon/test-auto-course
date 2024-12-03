package ua.edu.duan.book_shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.edu.duan.book_shop.enums.OrderState;

@Getter
@Setter
@Entity
@Table(name = "book_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_id")
    private long itemId;

    @Enumerated(EnumType.STRING)
    private OrderState state;
}
