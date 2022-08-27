package com.franc.app.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BizException extends RuntimeException {
    private final GlobalExceptionResult result;
}
