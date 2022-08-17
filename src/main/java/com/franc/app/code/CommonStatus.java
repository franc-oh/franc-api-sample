package com.franc.app.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 상태 (1:사용 9:사용금지)
 */
@Getter
@RequiredArgsConstructor
public enum CommonStatus {
    USING("1"),
    NON_USING("9");

    private final String status;
}
