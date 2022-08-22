package com.franc.app.my_membership.repository.entity;

import com.franc.app.code.CommonStatus;
import com.franc.app.code.MyMembershipStatus;
import com.franc.app.my_membership.repository.entity.key.MyMembershipKey;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 회원별 멤버십 가입정보
 */

@Entity
@IdClass(MyMembershipKey.class)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyMembership {
    @Id
    private Long accountId;

    @Id
    private Long mspId;

    @Column(columnDefinition = "char(1) default '1'")
    @Enumerated(EnumType.STRING)
    private MyMembershipStatus status = MyMembershipStatus.USING;

    @Column(nullable = false)
    private Integer totalAmt;

    @Column(nullable = false)
    private Integer usablePoint;

    private LocalDateTime withdrawalDate;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;

}
