package com.dino.springjpa.repository;

import com.dino.springjpa.entity.UserLazy;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserLazyRepository extends JpaRepository<UserLazy, Long> {

    @EntityGraph(value = "UserLazy.withOrder")
    @Query(value = " select a" +
            " from UserLazy a ")
    List<UserLazy> findAll();
}
