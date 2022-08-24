package com.franc.app.domain.my_membership.repository.entity;

import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipKey;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.global.code.converter.MyMembershipStatusConverter;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 회원별 멤버십 가입정보
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(MyMembershipKey.class)
@Getter
@ToString
@NoArgsConstructor
public class MyMembership {
    @Id
    private Long accountId;

    @Id
    private Long mspId;

    @Column(columnDefinition = "char(1) default '1'")
    @Convert(converter = MyMembershipStatusConverter.class)
    private MyMembershipStatus status;

    @Column(nullable = false)
    private Integer totalAmt;

    @Column(nullable = false)
    private Integer usablePoint;

    private LocalDateTime withdrawalDate;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;


    // 재가입
    public void rejoin() {
        this.status = MyMembershipStatus.USING;
        this.withdrawalDate = null;
    }

    // 탈퇴
    public void withdrawal(LocalDateTime withdrawalDate) {
        this.status = MyMembershipStatus.WITHDRAWAL;
        this.withdrawalDate = withdrawalDate;
    }

    @Builder
    public MyMembership(Long accountId, Long mspId, MyMembershipStatus status, Integer totalAmt, Integer usablePoint, LocalDateTime withdrawalDate, Long insertUser, Long updateUser) {
        this.accountId = accountId;
        this.mspId = mspId;
        this.status = status;
        this.totalAmt = totalAmt;
        this.usablePoint = usablePoint;
        this.withdrawalDate = withdrawalDate;
        this.insertUser = insertUser;
        this.updateUser = updateUser;
    }
}
