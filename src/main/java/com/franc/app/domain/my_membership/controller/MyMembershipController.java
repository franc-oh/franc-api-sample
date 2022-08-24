package com.franc.app.domain.my_membership.controller;

import com.franc.app.domain.my_membership.service.MyMembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/my_membership")
@RequiredArgsConstructor
public class MyMembershipController {

    private final MyMembershipService myMembershipService;


}
