package com.franc.app.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalExceptionResult {

    MY_MEMBERSHIP_JOIN_NON_PERMISSION_GRADE(HttpStatus.BAD_REQUEST, "해당 멤버십에 가입 불가능한 등급입니다."),
    MY_MEMBERSHIP_JOIN_DUPLICATED_MEMBERSHIP(HttpStatus.BAD_REQUEST, "이미 가입한 멤버십입니다."),
    MY_MEMBERSHIP_JOIN_NOT_AVAILABLE_REJOIN(HttpStatus.BAD_REQUEST, "탈퇴당일에는 재가입이 불가합니다."),
    MY_MEMBERSHIP_WITHDRAWAL_NOT_EXIST_MEMBERSHIP(HttpStatus.BAD_REQUEST, "가입하지 않은 멤버십입니다."),
    MY_MEMBERSHIP_WITHDRAWAL_NOT_AVAIL_STATUS(HttpStatus.BAD_REQUEST, "해당 멤버십은 탈퇴 불가능한 상태입니다."),

    PARAMETER_NOT_VALID(HttpStatus.BAD_REQUEST, "잘못된 요청 데이터입니다."),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "기타 오류");

    private final HttpStatus code;
    private final String message;

}
