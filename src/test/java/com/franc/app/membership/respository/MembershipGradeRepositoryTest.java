package com.franc.app.membership.respository;

import com.franc.app.global.code.AccountGrade;
import com.franc.app.global.code.CommonStatus;
import com.franc.app.domain.membership.repository.MembershipGradeRepository;
import com.franc.app.domain.membership.repository.entity.MembershipGrade;
import com.franc.app.domain.membership.repository.entity.key.MembershipGradeKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MembershipGradeRepositoryTest {

    @Autowired
    private MembershipGradeRepository membershipGradeRepository;

    @Test
    public void 가입허용등급조회() throws Exception {
        // #given
        Long mspId = 1L;
        AccountGrade accountGrade = AccountGrade._USER;
        CommonStatus status = CommonStatus.USING;
        MembershipGradeKey membershipGradeKey = new MembershipGradeKey().builder()
                .mspId(mspId)
                .accountGrade(accountGrade)
                .build();

        // #when
        MembershipGrade membershipGrade = membershipGradeRepository.findByIdAndStatusEqualsUsing(membershipGradeKey)
                .orElse(null);

        // #then
        assertThat(membershipGrade).isNotNull();
        assertThat(membershipGrade.getId().getMspId()).isEqualTo(mspId);
        assertThat(membershipGrade.getId().getAccountGrade()).isEqualTo(accountGrade);
        assertThat(membershipGrade.getStatus()).isEqualTo(status);
    }
}
