package com.dino.springjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(name = "UserLazy.withOrder", attributeNodes = { @NamedAttributeNode(value = "orders") })
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserLazy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<OrderLazy> orders = new ArrayList<>();
}