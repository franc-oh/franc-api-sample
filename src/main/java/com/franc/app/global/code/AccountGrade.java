package com.franc.app.global.code;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    // TODO : @JsonCreator 중복코드 제거
    @JsonCreator
    public static AccountGrade fromString(String str) {
        for(AccountGrade accountGrade : AccountGrade.values()) {
            if(accountGrade.getCode().equals(str))
                return accountGrade;
        }

        return null;
    }
}
