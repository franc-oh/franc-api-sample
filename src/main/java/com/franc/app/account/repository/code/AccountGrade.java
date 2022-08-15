package com.franc.app.account.repository.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 회원_등급 (VIP/USER/ADMIN)
 */
@Getter
@RequiredArgsConstructor
public enum AccountGrade {
    VIP("VIP"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String accountGrade;
}
