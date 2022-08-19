package com.franc.app.membership.repository.entity.key;

import com.franc.app.code.AccountGrade;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter @ToString
@NoArgsConstructor
public class MembershipGradeKey implements Serializable {
    private Long mspId;
    private String accountGrade;

    @Builder
    public MembershipGradeKey(Long mspId, String accountGrade) {
        this.mspId = mspId;
        this.accountGrade = accountGrade;
    }
}
