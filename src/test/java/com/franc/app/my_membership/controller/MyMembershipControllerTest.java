package com.franc.app.my_membership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franc.app.domain.my_membership.controller.MyMembershipController;
import com.franc.app.domain.my_membership.service.MyMembershipService;
import com.franc.app.domain.my_membership.vo.MyMembershipJoinRequestDTO;
import com.franc.app.domain.my_membership.vo.MyMembershipJoinResponseDTO;
import com.franc.app.global.code.AccountGrade;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.global.exception.BizExceptionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MyMembershipControllerTest {

    @InjectMocks
    private MyMembershipController myMembershipController;

    @Mock
    private MyMembershipService myMembershipService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(myMembershipController)
                .build();

        objectMapper = new ObjectMapper();
    }


    @ParameterizedTest
    @MethodSource("invalidMyMembershipJoinParams")
    public void 멤버십가입_실패_잘못된파라미터(Long accountId, Long mspId, String accountGrade) throws Exception {
        // #given
        String url = "/api/v1/my_membership/join";
        String content = "{\"accountId\":\"" + accountId + "\","
                + "\"mspId\":\"" + mspId + "\","
                + "\"accountGrade\":\"" + accountGrade + "\"}";

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(content)
        ).andDo(print());

        // #then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code", BizExceptionResult.MY_MEMBERSHIP_JOIN_BAD_REQUEST.getCode()).exists())
                .andExpect(jsonPath("message", BizExceptionResult.MY_MEMBERSHIP_JOIN_BAD_REQUEST.getMessage()).exists());

    }

    private static Stream<Arguments> invalidMyMembershipJoinParams() {
        return Stream.of(
                Arguments.of(null, null, null),
                Arguments.of(null, 1L, "USER"),
                Arguments.of(-1L, 1L, "USER"),
                Arguments.of(2L, null, "USER"),
                Arguments.of(2L, -1L, "USER"),
                Arguments.of(2L, 1L, null),
                Arguments.of(2L, 1L, "NONE")
        );
    }

    @Test
    public void 멤버십가입_성공() throws Exception {
        // #given
        String url = "/api/v1/my_membership/join";
        Long accountId = 2L;
        Long mspId = 1L;
        AccountGrade accountGrade = AccountGrade._USER;
        MyMembershipJoinRequestDTO request = MyMembershipJoinRequestDTO.builder()
                .accountId(accountId)
                .mspId(mspId)
                .accountGrade(accountGrade)
                .build();

        when(myMembershipService.join(any(MyMembershipJoinRequestDTO.class)))
                .thenReturn(new MyMembershipJoinResponseDTO().builder()
                        .accountId(accountId)
                        .mspId(mspId)
                        .status(MyMembershipStatus.USING)
                        .totalAmt(0)
                        .usablePoint(0)
                        .build()
                );

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // #then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("code", BizExceptionResult.MY_MEMBERSHIP_JOIN_BAD_REQUEST.getCode()).exists())
                .andExpect(jsonPath("message", BizExceptionResult.MY_MEMBERSHIP_JOIN_BAD_REQUEST.getMessage()).exists())
                .andExpect(jsonPath("accountId", accountId).exists())
                .andExpect(jsonPath("mspId", mspId).exists())
                .andExpect(jsonPath("status", MyMembershipStatus.USING).exists());
    }

}
