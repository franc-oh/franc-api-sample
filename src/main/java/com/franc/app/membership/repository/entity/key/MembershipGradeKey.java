package com.franc.app.membership.repository.entity.key;

import com.franc.app.code.AccountGrade;
import com.franc.app.code.converter.AccountGradeConverter;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @ToString
@NoArgsConstructor
public class MembershipGradeKey implements Serializable {
    private Long mspId;
    @Convert(converter = AccountGradeConverter.class)
    private AccountGrade accountGrade;

    @Builder
    public MembershipGradeKey(Long mspId, AccountGrade accountGrade) {
        this.mspId = mspId;
        this.accountGrade = accountGrade;
    }
}
