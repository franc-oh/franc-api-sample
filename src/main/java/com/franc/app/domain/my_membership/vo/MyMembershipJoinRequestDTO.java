package com.franc.app.domain.my_membership.vo;

import com.franc.app.global.code.AccountGrade;
import com.franc.app.global.code.converter.AccountGradeConverter;
import lombok.*;

import javax.persistence.Convert;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MyMembershipJoinRequestDTO {
    @NotNull
    @Min(1)
    private Long accountId;
    @NotNull
    @Min(1)
    private Long mspId;
    @NotNull
    @Convert(converter = AccountGradeConverter.class)
    private AccountGrade accountGrade;

    @Builder
    public MyMembershipJoinRequestDTO(Long accountId, Long mspId, AccountGrade accountGrade) {
        this.accountId = accountId;
        this.mspId = mspId;
        this.accountGrade = accountGrade;
    }
}
