package com.franc.app.domain.my_membership.repository.entity;

import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipBarcodeKey;
import com.franc.app.global.code.AccumFg;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 나의 멤버십_적립 바코드 관리
 */

@Entity
@IdClass(MyMembershipBarcodeKey.class)
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyMembershipBarcode {
    @Id
    @Column(length = 50)
    private String barCd;

    @Id
    @Column(updatable = false, nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long mspId;

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

    public void useBarcode(Long franchiseeId, Integer tradeAmt, Integer usePoint) {
        this.franchiseeId = franchiseeId;
        this.tradeAmt = tradeAmt == null ? 0 : tradeAmt;
        this.usePoint = usePoint == null ? 0 : usePoint;
        this.useDate = LocalDateTime.now();
    }
}
