package com.franc.app.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 적립구분 (A:적립 U:사용)
 */
@Getter
@RequiredArgsConstructor
public enum AccumFg {
    ACCUM("A"),
    USE("U");

    private final String accumFg;
}
