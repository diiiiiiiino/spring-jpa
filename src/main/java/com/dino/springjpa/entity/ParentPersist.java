package com.dino.springjpa.entity;

import lombok.Getter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ParentPersist {

    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private List<ChildPersist> children = new ArrayList<>();
}
