package com.franc.app.global.code;

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
}
