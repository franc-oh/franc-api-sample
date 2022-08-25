package com.franc.app.domain.my_membership.vo;

import com.franc.app.domain.my_membership.repository.entity.MyMembership;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.global.dto.ResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
public class MyMembershipJoinResponseDTO extends ResponseDTO {
    private Long accountId;
    private Long mspId;
    private MyMembershipStatus status;
    private Integer totalAmt;
    private Integer usablePoint;
    private LocalDateTime withdrawalDate;

    public void entityToDto(MyMembership e) {
        if(e == null) return;

        this.accountId = e.getAccountId();
        this.mspId = e.getMspId();
        this.status = e.getStatus();
        this.totalAmt = e.getTotalAmt();
        this.usablePoint = e.getUsablePoint();
        this.withdrawalDate = e.getWithdrawalDate();

    }
}
