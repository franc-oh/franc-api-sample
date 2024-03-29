package com.franc.app.global.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 고객_상태 (1:사용 9:사용금지 0:탈퇴)
 */
@Getter
@RequiredArgsConstructor
public enum AccountStatus implements CodeValue {
    USING("1", "사용"),
    NON_USING("9", "사용금지"),
    WITHDRAWAL("0", "탈퇴");

    private final String code;
    private final String value;

    @JsonCreator
    public static AccountStatus fromString(String str) {
        for(AccountStatus accountStatus : AccountStatus.values()) {
            if(accountStatus.getCode().equals(str))
                return accountStatus;
        }

        return null;
    }
}
