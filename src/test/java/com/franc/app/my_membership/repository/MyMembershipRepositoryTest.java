package com.franc.app.my_membership.repository;

import com.franc.app.domain.my_membership.repository.MyMembershipRepository;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.domain.my_membership.repository.entity.MyMembership;
import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipKey;
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
        MyMembership existMyMembership = myMembershipRepository.findById(key)
                .orElse(null);

        // #then
        assertThat(existMyMembership).isNull();
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
        MyMembership existMyMembership = myMembershipRepository.findById(key)
                .orElse(null);

        // #then
        assertThat(existMyMembership).isNotNull();
        assertThat(existMyMembership.getAccountId()).isEqualTo(accountId);
        assertThat(existMyMembership.getMspId()).isEqualTo(mspId);
        assertThat(existMyMembership.getStatus()).isEqualTo(MyMembershipStatus.USING);
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
