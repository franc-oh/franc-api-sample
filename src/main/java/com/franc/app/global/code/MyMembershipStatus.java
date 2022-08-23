package com.franc.app.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 나의 멤버십_상태 (1:사용 9:사용금지 0:탈퇴)
 */
@Getter
@RequiredArgsConstructor
public enum MyMembershipStatus implements CodeValue {
    USING("1", "사용"),
    NON_USING("9", "사용금지"),
    WITHDRAWAL("0", "탈퇴");

    private final String code;
    private final String value;
}
