package com.dino.springjpa.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ChildPersist {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private ParentPersist parent;
}
