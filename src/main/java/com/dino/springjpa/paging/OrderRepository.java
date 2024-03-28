package com.dino.springjpa.paging;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o" +
            " join fetch o.orderItems oi " +
            " join fetch oi.item i ")
    List<Order> findAllWithItem();

    @Query("select o from Order o")
    List<Order> findAllWithPageable(Pageable pageable);
}
