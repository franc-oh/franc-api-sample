package com.franc.app.my_membership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franc.app.domain.my_membership.controller.MyMembershipController;
import com.franc.app.domain.my_membership.dto.MyMembershipWithdrawalRequestDTO;
import com.franc.app.domain.my_membership.dto.MyMembershipWithdrawalResponseDTO;
import com.franc.app.domain.my_membership.service.MyMembershipService;
import com.franc.app.domain.my_membership.dto.MyMembershipJoinRequestDTO;
import com.franc.app.domain.my_membership.dto.MyMembershipJoinResponseDTO;
import com.franc.app.global.code.AccountGrade;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.global.exception.BizException;
import com.franc.app.global.exception.GlobalExceptionHandler;
import com.franc.app.global.exception.GlobalExceptionResult;
import com.franc.app.global.util.DateUtil;
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
import java.time.LocalDateTime;
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
                .setControllerAdvice(GlobalExceptionHandler.class) // Exception-Handler
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
                .andExpect(jsonPath("code").value(GlobalExceptionResult.PARAMETER_NOT_VALID.getCode().value()))
                .andExpect(jsonPath("message").value(GlobalExceptionResult.PARAMETER_NOT_VALID.getMessage()));

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
    public void 멤버십가입_실패_비즈니스로직_BIZ_EXCEPTION() throws Exception {
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

        // 제약조건 중에 하나만 테스트 -> 이미가입한멤버십인 경우
        when(myMembershipService.join(any(MyMembershipJoinRequestDTO.class)))
                .thenThrow(new BizException(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_DUPLICATED_MEMBERSHIP));

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // #then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_DUPLICATED_MEMBERSHIP.getCode().value()))
                .andExpect(jsonPath("message").value(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_DUPLICATED_MEMBERSHIP.getMessage()));
    }

    @Test
    public void 멤버십가입_실패_비즈니스로직_UNKNOWN_EXCEPTION() throws Exception {
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
                .thenThrow(new Exception(GlobalExceptionResult.UNKNOWN_EXCEPTION.getMessage()));

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // #then
        resultActions.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("code").value(GlobalExceptionResult.UNKNOWN_EXCEPTION.getCode().value()))
                .andExpect(jsonPath("message").value(GlobalExceptionResult.UNKNOWN_EXCEPTION.getMessage()));
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
                .andExpect(jsonPath("code").value("000"))
                .andExpect(jsonPath("message").value("정상"))
                .andExpect(jsonPath("accountId").value(accountId))
                .andExpect(jsonPath("mspId").value(mspId))
                .andExpect(jsonPath("status").value(MyMembershipStatus.USING.getCode()));
    }

    @ParameterizedTest
    @MethodSource("invalidMyMembershipWithdrawalParams")
    public void 멤버십탈퇴_실패_잘못된파라미터(Long accountId, Long mspId) throws Exception {
        // #given
        String url = "/api/v1/my_membership/withdrawal";
        String content = "{\"accountId\":\"" + accountId + "\","
                + "\"mspId\":\"" + mspId  + "\"}";

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(content)
        ).andDo(print());

        // #then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(GlobalExceptionResult.PARAMETER_NOT_VALID.getCode().value()))
                .andExpect(jsonPath("message").value(GlobalExceptionResult.PARAMETER_NOT_VALID.getMessage()));
    }

    @Test
    public void 멤버십탈퇴_실패_비즈니스로직오류_BIZ_EXCEPTION() throws Exception {
        // #given
        String url = "/api/v1/my_membership/withdrawal";
        Long accountId = 2L;
        Long mspId = 1L;
        MyMembershipWithdrawalRequestDTO request = MyMembershipWithdrawalRequestDTO.builder()
                .accountId(accountId)
                .mspId(mspId)
                .build();

        when(myMembershipService.withdrawal(any(MyMembershipWithdrawalRequestDTO.class)))
                .thenThrow(new BizException(GlobalExceptionResult.MY_MEMBERSHIP_WITHDRAWAL_NOT_AVAIL_STATUS));

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // #then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("code").value(GlobalExceptionResult.MY_MEMBERSHIP_WITHDRAWAL_NOT_AVAIL_STATUS.getCode().value()))
                .andExpect(jsonPath("message").value(GlobalExceptionResult.MY_MEMBERSHIP_WITHDRAWAL_NOT_AVAIL_STATUS.getMessage()));
    }

    @Test
    public void 멤버십탈퇴_실패_비즈니스로직오류_EXCEPTION() throws Exception {
        // #given
        String url = "/api/v1/my_membership/withdrawal";
        Long accountId = 2L;
        Long mspId = 1L;
        MyMembershipWithdrawalRequestDTO request = MyMembershipWithdrawalRequestDTO.builder()
                .accountId(accountId)
                .mspId(mspId)
                .build();

        when(myMembershipService.withdrawal(any(MyMembershipWithdrawalRequestDTO.class)))
                .thenThrow(new Exception(GlobalExceptionResult.UNKNOWN_EXCEPTION.getMessage()));

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // #then
        resultActions.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("code").value(GlobalExceptionResult.UNKNOWN_EXCEPTION.getCode().value()))
                .andExpect(jsonPath("message").value(GlobalExceptionResult.UNKNOWN_EXCEPTION.getMessage()));
    }

    @Test
    public void 멤버십탈퇴_성공() throws Exception {
        // #given
        String url = "/api/v1/my_membership/withdrawal";
        Long accountId = 2L;
        Long mspId = 1L;
        MyMembershipStatus status = MyMembershipStatus.WITHDRAWAL;
        LocalDateTime withdrawalDate = LocalDateTime.now();
        MyMembershipWithdrawalRequestDTO request = MyMembershipWithdrawalRequestDTO.builder()
                .accountId(accountId)
                .mspId(mspId)
                .build();

        when(myMembershipService.withdrawal(any(MyMembershipWithdrawalRequestDTO.class)))
                .thenReturn(new MyMembershipWithdrawalResponseDTO().builder()
                        .accountId(accountId)
                        .mspId(mspId)
                        .status(status)
                        .withdrawalDate(withdrawalDate)
                        .totalAmt(0)
                        .usablePoint(0)
                        .build());

        // #when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .accept(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                        .content(objectMapper.writeValueAsString(request))
        ).andDo(print());

        // #then
        resultActions.andExpect(status().isAccepted())
                .andExpect(jsonPath("code").value("000"))
                .andExpect(jsonPath("message").value("정상"))
                .andExpect(jsonPath("accountId").value(accountId))
                .andExpect(jsonPath("mspId").value(mspId))
                .andExpect(jsonPath("status").value(status.getCode()))
                .andExpect(jsonPath("withdrawalDate").value(DateUtil.formatLocalDateTime(withdrawalDate)));
    }


    private static Stream<Arguments> invalidMyMembershipWithdrawalParams() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, 1L),
                Arguments.of(-1L, 1L),
                Arguments.of(2L, null),
                Arguments.of(2L, -1L)
        );
    }

}
