package com.franc.app.global.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

/**
 * [코드] 상태 (1:사용 9:사용금지)
 */
@Getter
@RequiredArgsConstructor
public enum CommonStatus implements CodeValue {
    USING("1", "사용"),
    NON_USING("9", "사용금지");

    private final String code;
    private final String value;

    @JsonCreator
    public static CommonStatus fromString(String str) {
        for(CommonStatus commonStatus : CommonStatus.values()) {
            if(commonStatus.getCode().equals(str))
                return commonStatus;
        }

        return null;
    }
}
