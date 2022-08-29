package com.franc.app.domain.my_membership.dto;

import com.franc.app.global.code.AccountGrade;
import lombok.*;

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
    private AccountGrade accountGrade;

    @Builder
    public MyMembershipJoinRequestDTO(Long accountId, Long mspId, AccountGrade accountGrade) {
        this.accountId = accountId;
        this.mspId = mspId;
        this.accountGrade = accountGrade;
    }
}
