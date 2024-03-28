package com.dino.springjpa.paging;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Item {
    @Id @GeneratedValue
    private Long id;

    private String name;

    protected Item(String name) {
        this.name = name;
    }

    public static Item createItem(String name){
        return new Item(name);
    }
}