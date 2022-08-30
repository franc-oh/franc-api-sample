package com.franc.app.domain.my_membership.controller;

import com.franc.app.domain.my_membership.dto.MyMembershipWithdrawalRequestDTO;
import com.franc.app.domain.my_membership.service.MyMembershipService;
import com.franc.app.domain.my_membership.dto.MyMembershipJoinRequestDTO;
import com.franc.app.global.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/my_membership")
@RequiredArgsConstructor
@Slf4j
public class MyMembershipController {

    private final MyMembershipService myMembershipService;


    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid MyMembershipJoinRequestDTO request) throws Exception {
        ResponseDTO response;

        log.info("멤버십가입_Request => {}", request.toString());

        response = myMembershipService.join(request);

        if(response != null)
            log.info("멤버십가입_Response => {}", response.toString());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/withdrawal")
    public ResponseEntity<?> withdrawal(@RequestBody @Valid MyMembershipWithdrawalRequestDTO request) throws Exception {
        ResponseDTO response = null;

        log.info("멤버십탈퇴_Request => {}", request.toString());

        response = myMembershipService.withdrawal(request);

        if(response != null)
            log.info("멤버십탈퇴_Response => {}", response.toString());

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


}
