package com.franc.app.global.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * [코드] 회원_등급 (VIP/USER/ADMIN)
 */
@Getter
@RequiredArgsConstructor
public enum AccountGrade implements CodeValue {
    _VIP("VIP", "VIP고객"),
    _USER("USER", "일반고객"),
    _ADMIN("ADMIN", "관리자");

    private final String code;
    private final String value;


    private static final Map<String, AccountGrade> stringToEnum =
            Stream.of(values()).collect(toMap(Objects::toString, e -> e));
    @JsonCreator
    public static AccountGrade fromString(String str) {
        for(AccountGrade accountGrade : AccountGrade.values()) {
            if(accountGrade.getCode().equals(str))
                return accountGrade;
        }

        return null;
    }
    @JsonValue
    public String getCode() {
        return code;
    }
}
