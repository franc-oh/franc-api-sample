package com.franc.app.my_membership.repository;

import com.franc.app.code.MyMembershipStatus;
import com.franc.app.my_membership.repository.entity.MyMembership;
import com.franc.app.my_membership.repository.entity.key.MyMembershipKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class MyMembershipRepositoryTest {

    @Autowired
    private MyMembershipRepository myMembershipRepository;


    Long accountId = 2L;
    Long mspId = 1L;

    @Test
    public void MY멤버십가입정보가져오기_신규일경우() {
        // #given
        MyMembershipKey key = new MyMembershipKey().builder()
                .accountId(accountId)
                .mspId(mspId)
                .build();

        // #when
        MyMembership existsMyMembership = myMembershipRepository.findById(key)
                .orElse(null);

        // #then
        assertThat(existsMyMembership).isNull();
    }

    @Test
    public void MY멤버십가입정보가져오기_기등록일경우() {
        // #given
        insertMyMembership();

        MyMembershipKey key = new MyMembershipKey().builder()
                .accountId(accountId)
                .mspId(mspId)
                .build();

        // #when
        MyMembership existsMyMembership = myMembershipRepository.findById(key)
                .orElse(null);

        // #then
        assertThat(existsMyMembership).isNotNull();
        assertThat(existsMyMembership.getAccountId()).isEqualTo(accountId);
        assertThat(existsMyMembership.getMspId()).isEqualTo(mspId);
        assertThat(existsMyMembership.getStatus()).isEqualTo(MyMembershipStatus.USING);
    }

    public MyMembership buildEntity() {
        return new MyMembership().builder()
                .accountId(accountId)
                .mspId(mspId)
                .totalAmt(0)
                .usablePoint(0)
                .insertUser(1L)
                .build();
    }

    public void insertMyMembership() {
        // #given

        // #when
        MyMembership myMembership = myMembershipRepository.save(buildEntity());
        System.out.println(myMembershipRepository.findAll());

        // #then
    }



}
