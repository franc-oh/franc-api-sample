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
 * [코드] 나의 멤버십_상태 (1:사용 9:사용금지 0:탈퇴)
 */
@Getter
@RequiredArgsConstructor
public enum MyMembershipStatus implements CodeValue {
    USING("1", "사용"),
    NON_USING("9", "사용금지"),
    WITHDRAWAL("0", "탈퇴");

    private final String code;
    private final String value;

    // TODO : 중복코드
    private static final Map<String, MyMembershipStatus> stringToEnum =
            Stream.of(values()).collect(toMap(Objects::toString, e -> e));

    @JsonCreator
    public static MyMembershipStatus fromString(String str) {
        for(MyMembershipStatus myMembershipStatus : MyMembershipStatus.values()) {
            if(myMembershipStatus.getCode().equals(str))
                return myMembershipStatus;
        }

        return null;
    }

    @JsonValue
    public String getCode() {
        return code;
    }
}
