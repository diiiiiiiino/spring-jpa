package com.dino.springjpa.paging;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@DataJpaTest
public class PagingCollectionTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    private void beforeEach(){
        List<Item> items = new ArrayList<>();
        for(int i = 1; i <= 2; i++){
            Item item = Item.createItem("item" + i);
            items.add(item);
            itemRepository.save(item);
        }

        for(int i = 0; i < 2; i++){
            List<OrderItem> orderItems = new ArrayList<>();

            for(int j = 0; j < 2; j++){
                orderItems.add(OrderItem.createOrderItem(items.get(j), 1000 * (j + 1)));
            }

            orderRepository.save(Order.createOrder(orderItems));
        }

        entityManager.flush();
        entityManager.clear();
    }

    @DisplayName("컬렉션 페치 조인 최적화")
    @Test
    void findAllWithFetchJoin() {
        List<Order> allWithItem = orderRepository.findAllWithItem();

        for(Order order : allWithItem){
            for(OrderItem orderItem : order.getOrderItems()){
                int price = orderItem.getPrice();
                String name = orderItem.getItem().getName();
            }
        }

        Assertions.assertThat(allWithItem).hasSize(2);
    }

    @DisplayName("페이징과 한계돌파")
    @Test
    void pagingWithBatchSize() {
        Pageable pageable = PageRequest.of(0, 1);
        List<Order> allWithItem = orderRepository.findAllWithPageable(pageable);

        for(Order order : allWithItem){
            for(OrderItem orderItem : order.getOrderItems()){
                int price = orderItem.getPrice();
                String name = orderItem.getItem().getName();
            }
        }

        Assertions.assertThat(allWithItem).hasSize(1);
    }
}
