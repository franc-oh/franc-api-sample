package com.franc.app.domain.my_membership.controller;

import com.franc.app.domain.my_membership.service.MyMembershipService;
import com.franc.app.domain.my_membership.vo.MyMembershipJoinRequestDTO;
import com.franc.app.global.dto.ResponseDTO;
import com.franc.app.global.exception.BizException;
import com.franc.app.global.exception.BizExceptionResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/my_membership")
@RequiredArgsConstructor
@Slf4j
public class MyMembershipController {

    private final MyMembershipService myMembershipService;


    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid MyMembershipJoinRequestDTO request, BindingResult bindingResult) throws Exception {
        ResponseDTO response = new ResponseDTO();

        // TODO : GlobalException

        try {
            // #1. Request 검증
            log.info("멤버십가입_Request => " + request.toString());

            if(bindingResult.hasErrors()) {
                log.warn(bindingResult.getFieldErrors().toString());
                throw new BizException(BizExceptionResult.MY_MEMBERSHIP_JOIN_BAD_REQUEST);
            }

            // #2. 가입처리
            response = myMembershipService.join(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (BizException be) {
            HttpStatus beCode = be.getResult().getCode();
            String beMessage = be.getResult().getMessage();

            log.warn(beMessage);
            response.setCode(String.valueOf(beCode.value()));
            response.setMessage(beMessage);
            return new ResponseEntity<>(response, beCode);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setCode("999");
            response.setMessage("기타오류");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            log.info("멤버십가입_Response => " + response.toString());
        }
    }


}
