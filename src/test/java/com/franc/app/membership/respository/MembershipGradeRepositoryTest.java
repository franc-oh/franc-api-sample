package com.franc.app.membership.respository;

import com.franc.app.code.AccountGrade;
import com.franc.app.code.CommonStatus;
import com.franc.app.membership.repository.MembershipGradeRepository;
import com.franc.app.membership.repository.entity.MembershipGrade;
import com.franc.app.membership.repository.entity.key.MembershipGradeKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class MembershipGradeRepositoryTest {

    @Autowired
    private MembershipGradeRepository membershipGradeRepository;

    @Test
    public void 가입허용등급조회() {
        // #given
        Long mspId = 1L;
        AccountGrade accountGrade = AccountGrade.USER;
        CommonStatus status = CommonStatus.USING;
        MembershipGradeKey membershipGradeKey = new MembershipGradeKey().builder()
                .mspId(mspId)
                .accountGrade(accountGrade)
                .build();

        // #when
        MembershipGrade membershipGrade = membershipGradeRepository.findById(membershipGradeKey)
                .orElse(null);

        // #then
        assertThat(membershipGrade).isNotNull();
        assertThat(membershipGrade.getMspId()).isEqualTo(mspId);
        assertThat(membershipGrade.getAccountGrade()).isEqualTo(accountGrade);
        assertThat(membershipGrade.getStatus()).isEqualTo(status);
    }
}