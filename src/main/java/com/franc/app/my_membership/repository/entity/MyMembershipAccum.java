package com.franc.app.my_membership.repository.entity;

import com.franc.app.code.AccumFg;
import com.franc.app.code.CommonStatus;
import com.franc.app.my_membership.repository.entity.key.MyMembershipAccumKey;
import com.franc.app.my_membership.repository.entity.key.MyMembershipKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 나의 멤버십_적립내역
 */

@Entity
@IdClass(MyMembershipAccumKey.class)
@Getter
@NoArgsConstructor
public class MyMembershipAccum {
    @Id
    @Column(length = 20)
    private String pointAccumSeq;

    @Id
    private Long mspId;

    @Id
    private Long accountId;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, columnDefinition = "char(1) default 'A'")
    private AccumFg accumFg = AccumFg.ACCUM;

    @Column(nullable = false)
    private Integer tradePoint;

    @Column(nullable = false, length = 8)
    private String expireYmd;

}
