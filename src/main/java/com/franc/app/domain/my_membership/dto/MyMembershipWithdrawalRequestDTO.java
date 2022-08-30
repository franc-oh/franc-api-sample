package com.franc.app.domain.my_membership.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class MyMembershipWithdrawalRequestDTO {
    @NotNull
    @Min(1)
    private Long accountId;
    @NotNull
    @Min(1)
    private Long mspId;

    @Builder
    public MyMembershipWithdrawalRequestDTO(Long accountId, Long mspId) {
        this.accountId = accountId;
        this.mspId = mspId;
    }
}
