package com.franc.app.domain.franchisee.repository.entity;

import com.franc.app.global.code.CommonStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * [엔티티] - 가맹점 정보
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Franchisee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long franchiseeId;

    @Column(nullable = false, length = 200)
    private String franchiseeNm;

    @Column(length = 1, columnDefinition = "char(1) default '1'")
    @Enumerated(EnumType.STRING)
    private CommonStatus status = CommonStatus.USING;

    @Column(length = 20)
    private String telNo;

    @Column(length = 5)
    private String zipCd;

    @Column(length = 200)
    private String addr1;

    @Column(length = 200)
    private String addr2;

    @Column(length = 50)
    private String franchiseeMngNm;

    @Column(length = 11)
    private String franchiseeMngHphone;

    @Column(length = 40)
    private String franchiseeMngEmail;

    @Column(length = 2000)
    private String bigo;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;
}
