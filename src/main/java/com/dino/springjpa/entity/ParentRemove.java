package com.dino.springjpa.entity;

import lombok.Getter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ParentRemove {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<ChildRemove> children = new ArrayList<>();
}
