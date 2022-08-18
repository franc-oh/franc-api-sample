package com.franc.app.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 고객_상태 (1:사용 9:사용금지 0:탈퇴)
 */
@Getter
@RequiredArgsConstructor
public enum AccountStatus {
    USING('1', "사용"),
    NON_USING('9', "사용금지"),
    WITHDRAWAL('0', "탈퇴");

    private final char code;
    private final String value;
}
