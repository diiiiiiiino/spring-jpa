package com.dino.springjpa.paging;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class OrderItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer price;

    protected OrderItem(Item item, Integer price) {
        this.item = item;
        this.price = price;
    }

    public static OrderItem createOrderItem(Item item, Integer price){
        return new OrderItem(item, price);
    }

    void setOrder(Order order) {
        this.order = order;
    }
}
