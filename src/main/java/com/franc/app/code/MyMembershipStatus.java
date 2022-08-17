package com.franc.app.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 나의 멤버십_상태 (1:사용 9:사용금지 0:탈퇴)
 */
@Getter
@RequiredArgsConstructor
public enum MyMembershipStatus {
    USING("1"),
    NON_USING("9"),
    WITHDRAWAL("0");

    private final String status;
}
