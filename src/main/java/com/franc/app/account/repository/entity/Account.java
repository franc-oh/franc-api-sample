package com.franc.app.account.repository.entity;

import com.franc.app.code.AccountGrade;
import com.franc.app.code.AccountStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * [엔티티] - 회원 정보
 */

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, length = 50)
    private String accountNm;

    @Column(length = 1, columnDefinition = "char(1) default '1'")
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.USING;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 8)
    private String birth;

    @Column(nullable = false, length = 11)
    private String hphone;

    @Column(length = 10)  @ColumnDefault("'USER'")
    @Enumerated(EnumType.STRING)
    private AccountGrade grade = AccountGrade.USER;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime insertDate;

    @Column(updatable = false)
    private Long insertUser;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long updateUser;

}
