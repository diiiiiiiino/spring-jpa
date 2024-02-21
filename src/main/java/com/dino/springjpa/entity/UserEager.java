package com.dino.springjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(name = "UserEager.withOrder", attributeNodes = { @NamedAttributeNode("orders") })
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    List<OrderEager> orders = new ArrayList<>();
}