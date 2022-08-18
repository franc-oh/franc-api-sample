package com.franc.app.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 회원_등급 (VIP/USER/ADMIN)
 */
@Getter
@RequiredArgsConstructor
public enum AccountGrade {
    VIP("VIP", "VIP고객"),
    USER("USER", "일반고객"),
    ADMIN("ADMIN", "관리자");

    private final String code;
    private final String value;
}
