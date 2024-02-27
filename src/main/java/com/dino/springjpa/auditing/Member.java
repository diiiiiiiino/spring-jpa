package com.dino.springjpa.auditing;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Member extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String name;

    protected Member(String name) {
        this.name = name;
    }
}
