package com.franc.app.account.repository.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 회원_상태 (1:사용 9:사용금지 0:탈퇴)
 */
@Getter
@RequiredArgsConstructor
public enum AccountStatus {
    USING("1"),
    NON_USING("9"),
    WITHDRAWAL("0");

    private final String accountStatus;
}
