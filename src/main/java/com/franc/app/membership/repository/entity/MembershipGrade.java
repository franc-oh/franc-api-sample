package com.franc.app.membership.repository.entity;

import com.franc.app.membership.repository.code.MembershipGradeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 멤버십별 가입가능한 회원등급
 */

@Entity
@IdClass(MembershipGradeKey.class)
@Getter
@NoArgsConstructor
public class MembershipGrade {

    @Id
    private Long mspId;

    @Id
    private String accountGrade;

    @Column(length = 1, columnDefinition = "char(1) default '1'")
    @Enumerated(EnumType.STRING)
    private MembershipGradeStatus status = MembershipGradeStatus.USING;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;
}
