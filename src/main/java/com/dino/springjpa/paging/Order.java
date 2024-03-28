package com.dino.springjpa.paging;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@EqualsAndHashCode
@NoArgsConstructor
public class Order {
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(List<OrderItem> orderItems){
        Order order = new Order();
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }

        return order;
    }
}
