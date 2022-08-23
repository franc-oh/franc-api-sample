package com.franc.app.domain.my_membership.vo;

import com.franc.app.global.code.AccountGrade;
import lombok.*;

@NoArgsConstructor
@Getter @Setter
@ToString
public class MyMembershipReqDTO {
    private Long accountId;
    private Long mspId;
    private AccountGrade accountGrade;

    @Builder
    public MyMembershipReqDTO(Long accountId, Long mspId, AccountGrade accountGrade) {
        this.accountId = accountId;
        this.mspId = mspId;
        this.accountGrade = accountGrade;
    }
}
