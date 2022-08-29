package com.franc.app.global.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 멤버십 적립구분 (1:정액 2:정률)
 */
@Getter
@RequiredArgsConstructor
public enum MspAccumFg implements CodeValue {
    AMT("1", "정액"),
    RAT("2", "정률");

    private final String code;
    private final String value;

    @JsonCreator
    public static MspAccumFg fromString(String str) {
        for(MspAccumFg mspAccumFg : MspAccumFg.values()) {
            if(mspAccumFg.getCode().equals(str))
                return mspAccumFg;
        }

        return null;
    }
}
