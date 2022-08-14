package com.franc.app.account.repository.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 회원_등급 (VIP/USER)
 */
@Getter
@RequiredArgsConstructor
public enum AccountGrade {
    VIP("VIP"),
    USER("USER");

    private final String accountGrade;

}
