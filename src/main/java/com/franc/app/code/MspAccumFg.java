package com.franc.app.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 멤버십 적립구분 (1:정액 2:정률)
 */
@Getter
@RequiredArgsConstructor
public enum MspAccumFg {
    AMT("1"),
    RAT("2");

    private final String mspAccumFg;
}
