package com.franc.app.membership.repository.entity.key;

import com.franc.app.code.AccountGrade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MembershipGradeKey implements Serializable {
    private Long mspId;
    private AccountGrade accountGrade;

    @Builder
    public MembershipGradeKey(Long mspId, AccountGrade accountGrade) {
        this.mspId = mspId;
        this.accountGrade = accountGrade;
    }
}
