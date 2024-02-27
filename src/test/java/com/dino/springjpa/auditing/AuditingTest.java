package com.dino.springjpa.auditing;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AuditingTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("1. Auditing 기능으로 생성자/수정자 정보 입력 확인")
    @Test
    void hasCreateAndModifyInfo () {
        Member member = new Member("Faker");
        
        member = memberRepository.save(member);

        Assertions.assertThat(member.getCreatedBy()).isNotBlank();
        Assertions.assertThat(member.getLastModifiedBy()).isNotBlank();
    }
}
