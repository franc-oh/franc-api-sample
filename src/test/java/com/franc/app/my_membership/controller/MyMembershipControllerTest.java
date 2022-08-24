package com.franc.app.my_membership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franc.app.domain.my_membership.controller.MyMembershipController;
import com.franc.app.domain.my_membership.service.MyMembershipService;
import com.franc.app.domain.my_membership.vo.MyMembershipJoinRequestDTO;
import com.franc.app.global.code.AccountGrade;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MyMembershipControllerTest {

    @InjectMocks
    private MyMembershipController myMembershipController;

    @Mock
    private MyMembershipService myMembershipService;

    private MockMvc mockMvc;
    private JSONObject jsonObject;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(myMembershipController)
                .build();
    }

    @Test
    public void 초기화테스트() {
        assertThat(myMembershipController).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void 멤버십가입_실패_Request검증오류() throws Exception {
        // #given
        String url = "/api/v1/my_membership";
        MyMembershipJoinRequestDTO request = new MyMembershipJoinRequestDTO().builder()
                .accountId(2L)
                .mspId(1L)
                .accountGrade(AccountGrade._USER)
                .build();

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
        );

        // #then
        resultActions.andExpect(status().isBadRequest());
    }

}
