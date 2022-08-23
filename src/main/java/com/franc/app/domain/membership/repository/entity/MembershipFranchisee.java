package com.franc.app.domain.membership.repository.entity;

import com.franc.app.domain.membership.repository.entity.key.MembershipFranchiseeKey;
import com.franc.app.global.code.CommonStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 멤버십별 가맹점
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(MembershipFranchiseeKey.class)
@Getter
@NoArgsConstructor
public class MembershipFranchisee {

    @Id
    private Long mspId;

    @Id
    private Long franchiseeId;

    @Column(length = 1, columnDefinition = "char(1) default '1'")
    @Enumerated(EnumType.STRING)
    private CommonStatus status = CommonStatus.USING;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;
}
