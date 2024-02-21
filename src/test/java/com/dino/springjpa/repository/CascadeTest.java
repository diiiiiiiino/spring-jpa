package com.dino.springjpa.repository;

import com.dino.springjpa.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@DataJpaTest
public class CascadeTest {

    @PersistenceContext
    private EntityManager entityManager;

    @DisplayName("영속성 전이 :  저장")
    @Test
    void persistCascadeTest() {
        ChildPersist child1 = new ChildPersist();
        ChildPersist child2 = new ChildPersist();

        ParentPersist parent = new ParentPersist();
        child1.setParent(parent);
        child2.setParent(parent);
        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        entityManager.persist(parent);

        List<ChildPersist> childs = entityManager.createQuery(
                " select a" +
                        " from ChildPersist a ", ChildPersist.class)
                .getResultList();

        Assertions.assertEquals(2, childs.size());
    }

    @DisplayName("영속성 전이 :  삭제")
    @Test
    void removeCascadeTest() {
        ParentRemove parent = new ParentRemove();
        ChildRemove child1 = new ChildRemove();
        ChildRemove child2 = new ChildRemove();
        child1.setParent(parent);
        child2.setParent(parent);

        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        entityManager.persist(parent);
        entityManager.persist(child1);
        entityManager.persist(child2);

        entityManager.remove(parent);

        List<ChildRemove> childs = entityManager.createQuery(
                        " select a" +
                        " from ChildRemove a ", ChildRemove.class)
                .getResultList();

        Assertions.assertTrue(childs.isEmpty());
    }

    @DisplayName("고아 객체")
    @Test
    void orphanRemovalTest() {
        ParentOrphan parent = new ParentOrphan();
        ChildOrphan child1 = new ChildOrphan();
        ChildOrphan child2 = new ChildOrphan();
        child1.setParent(parent);
        child2.setParent(parent);

        parent.getChildren().add(child1);
        parent.getChildren().add(child2);

        entityManager.persist(parent);
        entityManager.persist(child1);
        entityManager.persist(child2);

        entityManager.remove(parent);

        List<ChildOrphan> childs = entityManager.createQuery(
                        " select a" +
                                " from ChildOrphan a ", ChildOrphan.class)
                .getResultList();

        Assertions.assertTrue(childs.isEmpty());
    }
}
