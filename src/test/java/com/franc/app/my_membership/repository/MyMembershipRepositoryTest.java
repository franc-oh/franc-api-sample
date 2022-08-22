package com.franc.app.my_membership.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MyMembershipRepositoryTest {

    @Autowired
    private MyMembershipRepository myMembershipRepository;

    @Test
    public void MY멤버십가입여부체크() {
        // #given
        Long accountId = 1L;
        Long mspId = 1L;

        // #when

        // #then
    }

}
