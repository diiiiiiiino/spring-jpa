package com.dino.springjpa.repository;

import com.dino.springjpa.entity.OrderEager;
import com.dino.springjpa.entity.OrderLazy;
import com.dino.springjpa.entity.UserEager;
import com.dino.springjpa.entity.UserLazy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@DataJpaTest
public class NPlusOneTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserEagerRepository userEagerRepository;

    @Autowired
    private UserLazyRepository userLazyRepository;

    @BeforeEach
    void init(){
        for(int i = 1; i <= 5; i++){
            UserEager userEager = UserEager.builder()
                    .name("eagerUser" + i)
                    .build();

            UserLazy userLazy = UserLazy.builder()
                    .name("lazyUser" + i)
                    .build();

            entityManager.persist(userEager);
            entityManager.persist(userLazy);

            for(int j = 0; j < 2; j++){
                OrderEager orderEager = OrderEager.builder()
                        .item("eagerItem" + j)
                        .user(userEager)
                        .build();

                OrderLazy orderLazy = OrderLazy.builder()
                        .item("lazyItem" + j)
                        .user(userLazy)
                        .build();

                entityManager.persist(orderEager);
                entityManager.persist(orderLazy);
            }
        }

        entityManager.clear(); //영속성 컨텍스트를 초기화하지 않으면 1차캐시에서 데이터를 가져오기때문에 정확한 쿼리 호출 확인이 불가함
    }

    @DisplayName("1-1. N + 1 발생 (FetchType.EAGER 일때)")
    @Test
    void nPlusOneEager1_1() {
        List<UserEager> results = entityManager.createQuery(
                " select a " +
                        " from UserEager a", UserEager.class)
                .getResultList();

        Assertions.assertEquals(5, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("1-2. N + 1 해결 (FetchType.EAGER 일때 해결방법 : join fetch 중복데이터 조회)")
    @Test
    void nPlusOneEager1_2() {
        List<UserEager> results = entityManager.createQuery(
                        " select a " +
                                " from UserEager a " +
                                " join fetch a.orders ", UserEager.class)
                .getResultList();

        Assertions.assertEquals(10, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("1-2-2. N + 1 해결 (FetchType.EAGER 일때 해결방법 : join fetch 중복데이터 조회 해결)")
    @Test
    void nPlusOneEager1_2_2() {
        List<UserEager> results = entityManager.createQuery(
                        " select distinct a " +
                                " from UserEager a " +
                                " join fetch a.orders ", UserEager.class)
                .getResultList();

        Assertions.assertEquals(5, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("1-3. N + 1 해결 (FetchType.EAGER 일때 해결방법 : entity graph - createQuery)")
    @Test
    void nPlusOneEager1_3() {
        List<UserEager> results = entityManager.createQuery(
                        " select a " +
                                " from UserEager a ", UserEager.class)
                .setHint("jakarta.persistence.fetchgraph", entityManager.getEntityGraph("UserEager.withOrder"))
                .getResultList();

        Assertions.assertEquals(5, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("1-4. N + 1 해결 (FetchType.EAGER 일때 해결방법 : entity graph - JPA Repository)")
    @Test
    void nPlusOneEager1_4() {
        List<UserEager> results = userEagerRepository.findAll();

        Assertions.assertEquals(5, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("2-1. N + 1 발생 (FetchType.LAZY 일 때)")
    @Test
    void nPlusOneLazy2_1() {
        List<UserLazy> results = entityManager.createQuery(
                " select a " +
                        " from UserLazy a", UserLazy.class)
                .getResultList();

        for(UserLazy user : results){
            user.getOrders().size();
        }

        Assertions.assertEquals(5, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("2-2. N + 1 해결 (FetchType.LAZY 일 때 해결방법 : join fetch 중복데이터 조회)")
    @Test
    void nPlusOneLazy2_2() {
        List<UserLazy> results = entityManager.createQuery(
                        " select a " +
                                " from UserLazy a" +
                                " join fetch a.orders ", UserLazy.class)
                .getResultList();

        for(UserLazy user : results){
            user.getOrders().size();
        }

        Assertions.assertEquals(10, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("2-2-2. N + 1 해결 (FetchType.LAZY 일 때 해결방법 : join fetch 중복데이터 해결)")
    @Test
    void nPlusOneLazy2_2_2() {
        List<UserLazy> results = entityManager.createQuery(
                        " select distinct a " +
                                " from UserLazy a" +
                                " join fetch a.orders ", UserLazy.class)
                .getResultList();

        for(UserLazy user : results){
            user.getOrders().size();
        }

        Assertions.assertEquals(5, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("2-3. N + 1 해결 (FetchType.LAZY 일 때 해결방법 : entity graph - createQuery)")
    @Test
    void nPlusOneLazy2_3() {
        List<UserLazy> results = entityManager.createQuery(
                        " select a " +
                                " from UserLazy a ", UserLazy.class)
                .setHint("jakarta.persistence.fetchgraph", entityManager.getEntityGraph("UserLazy.withOrder"))
                .getResultList();

        for(UserLazy user : results){
            user.getOrders().size();
        }

        Assertions.assertEquals(5, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }

    @DisplayName("2-4. N + 1 해결 (FetchType.LAZY 일 때 해결방법 : entity graph - JPA Repository)")
    @Test
    void nPlusOneLazy2_4() {
        List<UserLazy> results = userLazyRepository.findAll();

        for(UserLazy user : results){
            user.getOrders().size();
        }

        Assertions.assertEquals(5, results.size());
        Assertions.assertEquals(2, results.get(0).getOrders().size());
    }
}
