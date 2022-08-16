package com.franc.app.membership.repository.entity;

import com.franc.app.membership.repository.code.MembershipStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 멤버십 정보
 */

@Entity
@Getter
@NoArgsConstructor
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mspId;

    @Column(nullable = false, length = 200)
    private String mspNm;

    @Column(length = 1, columnDefinition = "char(1) default '1'")
    @Enumerated(EnumType.STRING)
    private MembershipStatus status = MembershipStatus.USING;

    @Column(length = 200)
    private String mspInfo;

    @Column(length = 300)
    private String mspImgUrl;

    @Column(length = 100)
    private String homepageUrl;

    @Column(length = 1) @ColumnDefault("2")
    private Character mspAccumFg = '2';

    @ColumnDefault("0")
    private Integer mspAccumAmt = 0;

    @ColumnDefault("10")
    private Integer mspAccumRat = 10;

    @ColumnDefault("0")
    private Integer mspAccumMinAmt = 0;

    @ColumnDefault("0")
    private Integer mspPointExpireDays = 0;

    @Column(length = 2000)
    private String bigo;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;
}
