package com.franc.app.domain.membership.repository.entity;

import com.franc.app.global.code.CommonStatus;
import com.franc.app.global.code.converter.CommonStatusConverter;
import com.franc.app.domain.membership.repository.entity.key.MembershipGradeKey;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 멤버십별 가입가능한 회원등급
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@ToString
@NoArgsConstructor
public class MembershipGrade {

    @EmbeddedId
    private MembershipGradeKey id;

    @Column(columnDefinition = "char(1) default '1'")
    @Convert(converter = CommonStatusConverter.class)
    private CommonStatus status = CommonStatus.USING;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;


    @Builder
    public MembershipGrade(MembershipGradeKey id, CommonStatus status, Long insertUser, Long updateUser) {
        this.id = id;
        this.status = status;
        this.insertUser = insertUser;
        this.updateUser = updateUser;
    }
}
