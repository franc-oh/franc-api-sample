package com.franc.app.domain.my_membership.repository.entity;

import com.franc.app.global.code.AccumFg;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * [엔티티] - 나의 멤버십_적립내역
 */

@Entity
@Getter
@NoArgsConstructor
public class MyMembershipAccum {
    @Id
    @Column(length = 20)
    private String pointAccumSeq;

    @Column(nullable = false)
    private Long mspId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long franchiseeId;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, columnDefinition = "char(1) default 'A'")
    private AccumFg accumFg = AccumFg.ACCUM;

    @Column(nullable = false)
    private Integer tradePoint;

    @Column(nullable = false, length = 8)
    private String expireYmd;

}
