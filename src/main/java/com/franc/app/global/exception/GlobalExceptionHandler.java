package com.franc.app.global.exception;

import com.franc.app.global.dto.ExceptionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * '파라미터 유효성 검증 실패' 처리
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponseDTO> methodArgmentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();

        String errorCode = String.valueOf(GlobalExceptionResult.PARAMETER_NOT_VALID.getCode().value());
        String errorMessage = GlobalExceptionResult.PARAMETER_NOT_VALID.getMessage();

        return ResponseEntity.status(GlobalExceptionResult.PARAMETER_NOT_VALID.getCode())
                .body(buildExceptionResponse(errorCode, errorMessage));
    }

    /**
     * '비즈니스 예외' 처리
     * @param e
     * @return
     */
    @ExceptionHandler({BizException.class})
    public ResponseEntity<ExceptionResponseDTO> bizExceptionHandler(BizException e) {
        e.printStackTrace();

        String errorCode = String.valueOf(e.getResult().getCode().value());
        String errorMessage = e.getResult().getMessage();

        return ResponseEntity.status(e.getResult().getCode())
                .body(buildExceptionResponse(errorCode, errorMessage));
    }

    /**
     * '기타오류' 처리
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionResponseDTO> exceptionHandler(Exception e) {
        e.printStackTrace();

        String errorCode = String.valueOf(GlobalExceptionResult.UNKNOWN_EXCEPTION.getCode().value());
        String errorMessage = e.getMessage() != null
                ? e.getMessage()
                : GlobalExceptionResult.UNKNOWN_EXCEPTION.getMessage();

        return ResponseEntity.status(GlobalExceptionResult.UNKNOWN_EXCEPTION.getCode())
                .body(buildExceptionResponse(errorCode, errorMessage));
    }

    // 예외핸들러의 body를 빌드
    public ExceptionResponseDTO buildExceptionResponse(String errorCode, String errorMessage) {
        return new ExceptionResponseDTO().builder()
                    .code(errorCode)
                    .message(errorMessage)
                    .build();
    }
}
