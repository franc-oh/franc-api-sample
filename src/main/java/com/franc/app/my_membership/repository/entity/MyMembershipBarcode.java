package com.franc.app.my_membership.repository.entity;

import com.franc.app.code.AccumFg;
import com.franc.app.code.CommonStatus;
import com.franc.app.my_membership.repository.entity.key.MyMembershipBarcodeKey;
import com.franc.app.my_membership.repository.entity.key.MyMembershipKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 나의 멤버십_적립 바코드 관리
 */

@Entity
@IdClass(MyMembershipBarcodeKey.class)
@Getter
@NoArgsConstructor
public class MyMembershipBarcode {
    @Id
    @Column(length = 50)
    private String barCd;

    @Id
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long mspId;

    @Column(nullable = false)
    private Long franchiseeId;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, columnDefinition = "char(1) default 'A'")
    private AccumFg accumFg = AccumFg.ACCUM;

    @Column(nullable = false)
    private Integer tradeAmt;

    @Column(nullable = false)
    private Integer usePoint;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    private LocalDateTime useDate;

}
