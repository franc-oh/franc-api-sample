package com.franc.app.membership.repository.entity;

import com.franc.app.code.AccountGrade;
import com.franc.app.code.CommonStatus;
import com.franc.app.code.converter.AccountGradeConverter;
import com.franc.app.membership.repository.entity.key.MembershipGradeKey;
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
    @Column(length = 10)
    //@Enumerated(EnumType.STRING)
    @Convert(converter = AccountGradeConverter.class)
    private AccountGrade accountGrade;

    @Column(columnDefinition = "char(1) default '1'")
    @Convert(converter = CommonStatus.class)
    //@Enumerated(EnumType.STRING)
    private CommonStatus status = CommonStatus.USING;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;
}
