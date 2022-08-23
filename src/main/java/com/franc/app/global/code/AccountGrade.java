package com.franc.app.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 회원_등급 (VIP/USER/ADMIN)
 */
@Getter
@RequiredArgsConstructor
public enum AccountGrade implements CodeValue {
    _VIP("VIP", "VIP고객"),
    _USER("USER", "일반고객"),
    _ADMIN("ADMIN", "관리자");

    private final String code;
    private final String value;
}
