package com.franc.app.my_membership.repository;

import com.franc.app.my_membership.repository.entity.MyMembership;
import com.franc.app.my_membership.repository.entity.key.MyMembershipKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class MyMembershipRepositoryTest {

    @Autowired
    private MyMembershipRepository myMembershipRepository;

    @Test
    public void MY멤버십가입정보가져오기() {
        // #given
        Long accountId = 2L;
        Long mspId = 1L;
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

}
