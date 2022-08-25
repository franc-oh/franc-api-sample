package com.franc.app.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BizExceptionResult {

    MY_MEMBERSHIP_JOIN_NON_PERMISSION_GRADE(HttpStatus.BAD_REQUEST, "해당 멤버십에 가입 불가능한 등급입니다."),
    MY_MEMBERSHIP_JOIN_DUPLICATED_MEMBERSHIP(HttpStatus.BAD_REQUEST, "이미 가입한 멤버십입니다."),
    MY_MEMBERSHIP_JOIN_NOT_AVAILABLE_REJOIN(HttpStatus.BAD_REQUEST, "탈퇴당일에는 재가입이 불가합니다."),

    MY_MEMBERSHIP_JOIN_BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청 데이터입니다.");

    private final HttpStatus code;
    private final String message;

}
