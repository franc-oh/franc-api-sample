package com.franc.app.global.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 적립구분 (A:적립 U:사용)
 */
@Getter
@RequiredArgsConstructor
public enum AccumFg implements CodeValue {
    ACCUM("A", "적립"),
    USE("U", "사용");

    private final String code;
    private final String value;
}
