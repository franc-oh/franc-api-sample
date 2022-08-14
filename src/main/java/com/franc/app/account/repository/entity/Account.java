package com.franc.app.account.repository.entity;

import com.franc.app.account.repository.code.AccountGrade;
import com.franc.app.account.repository.code.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * ================= 회원 정보 =================
 * ACCOUNT_ID [*]                    - 회원ID
 * ACCOUNT_NM                        - 회원이름
 * STATUS                            - 상태 (1:사용 9:사용금지 0:탈퇴)
 * EMAIL                             - 이메일
 * BIRTH                             - 생년월일 (yyyymmdd)
 * HPHONE                            - 휴대폰번호
 * GRADE                             - 회원등급 (VIP/GOLD/SILVER/BRONZE/COMMON)
 * INSERT_DATE                       - 등록일자 (yyyymmddhhmmss)
 * INSERT_USER                       - 등록자
 * UPDATE_DATE                       - 수정일자 (yyyymmddhhmmss)
 * UPDATE_USER                       - 수정자
 */
@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, length = 20)
    private String accountNm;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(length = 8)
    private String birth;

    @Column(nullable = false, length = 11)
    private String hphone;

    @Enumerated(EnumType.STRING)
    private AccountGrade grade;

    @CreationTimestamp
    @Column(nullable = false, length = 20, updatable = false)
    private LocalDateTime insertDate;

    @Column(length = 20, updatable = false)
    private String insertUser;

    @UpdateTimestamp
    @Column(length = 20)
    private LocalDateTime updatedAt;

    private String updateUser;

}
