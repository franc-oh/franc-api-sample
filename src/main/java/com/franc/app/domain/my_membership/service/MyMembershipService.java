package com.franc.app.domain.my_membership.service;

import com.franc.app.domain.my_membership.dto.MyMembershipWithdrawalRequestDTO;
import com.franc.app.domain.my_membership.dto.MyMembershipWithdrawalResponseDTO;
import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipKey;
import com.franc.app.domain.my_membership.dto.MyMembershipJoinResponseDTO;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.domain.membership.repository.MembershipGradeRepository;
import com.franc.app.domain.membership.repository.entity.MembershipGrade;
import com.franc.app.domain.membership.repository.entity.key.MembershipGradeKey;
import com.franc.app.domain.my_membership.repository.MyMembershipRepository;
import com.franc.app.domain.my_membership.repository.entity.MyMembership;
import com.franc.app.domain.my_membership.dto.MyMembershipJoinRequestDTO;
import com.franc.app.global.dto.ResponseDTO;
import com.franc.app.global.exception.BizException;
import com.franc.app.global.exception.GlobalExceptionResult;
import com.franc.app.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyMembershipService {

    private final MembershipGradeRepository membershipGradeRepository;
    private final MyMembershipRepository myMembershipRepository;

    /**
     * 멤버십 가입
     * @param request
     * @return response
     * @throws Exception
     */
    @Transactional
    public MyMembershipJoinResponseDTO join(MyMembershipJoinRequestDTO request) throws Exception {
        MyMembershipJoinResponseDTO response = null;

        // #1. 가입가능한 멤버십인지 -- 가입등급 체크
        MembershipGrade availMembershipGrade =
                membershipGradeRepository.findByIdAndStatusEqualsUsing(new MembershipGradeKey().builder()
                        .accountGrade(request.getAccountGrade())
                        .mspId(request.getMspId())
                        .build())
                .orElseThrow(() -> new BizException(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_NON_PERMISSION_GRADE));

        log.debug("멤버십가입_가입등급체크 => {}", availMembershipGrade.toString());

        // #2. 기등록여부 체크
        MyMembership existMyMembership = myMembershipRepository.findById(new MyMembershipKey().builder()
                        .accountId(request.getAccountId())
                        .mspId(request.getMspId())
                        .build())
                .orElse(null);

        MyMembershipStatus existMembershipStatus = null;
        LocalDateTime withdrawalDate = null;
        if(existMyMembership != null) {
            existMembershipStatus = existMyMembership.getStatus();
            withdrawalDate = existMyMembership.getWithdrawalDate();

            log.debug("멤버십가입_기등록여부체크 => {}", existMyMembership.toString());
        }

        // ##2-1. '사용' 또는 '중지' 상태의 멤버십인 경우 => 기등록 멤버십
        if(MyMembershipStatus.USING.equals(existMembershipStatus)
                || MyMembershipStatus.NON_USING.equals(existMembershipStatus))
            throw new BizException(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_DUPLICATED_MEMBERSHIP);

        // ##2-2. '탈퇴'상태의 멤버십인 경우 => 탈퇴당일 재가입 불가
        if(MyMembershipStatus.WITHDRAWAL.equals(existMembershipStatus)
                && DateUtil.isEqualNowDate(withdrawalDate))
            throw new BizException(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_NOT_AVAILABLE_REJOIN);

        // #3. 가입 처리
        response = new MyMembershipJoinResponseDTO();
        // ##3-1. 신규가입
        if(!MyMembershipStatus.WITHDRAWAL.equals(existMembershipStatus)) {
            response.entityToDto(
                    myMembershipRepository.save(new MyMembership().builder()
                    .accountId(request.getAccountId())
                    .mspId(request.getMspId())
                    .build()));

        // ##3-2. 재가입
        } else {
            existMyMembership.rejoin();
            response.entityToDto(existMyMembership);
        }

        log.debug("멤버십가입_가입처리 => {}", response.toString());

        return response;
    }

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @Transactional
    public MyMembershipWithdrawalResponseDTO withdrawal(MyMembershipWithdrawalRequestDTO request) throws Exception {
        MyMembershipWithdrawalResponseDTO response = null;
        // #1. 멤버십 조회 및 체크
        MyMembership myMembership = myMembershipRepository.findById(new MyMembershipKey().builder()
                .accountId(request.getAccountId())
                .mspId(request.getMspId())
                .build())
                .orElseThrow(() -> new BizException(GlobalExceptionResult.MY_MEMBERSHIP_WITHDRAWAL_NOT_EXIST_MEMBERSHIP));

        log.debug("멤버십탈퇴_멤버십조회 => {}", myMembership.toString());

        // 상태가 '사용' 중인 멤버십만 탈퇴가능
        if(MyMembershipStatus.USING != myMembership.getStatus())
            throw new BizException(GlobalExceptionResult.MY_MEMBERSHIP_WITHDRAWAL_NOT_AVAIL_STATUS);

        // #2. 멤버십 탈퇴
        response = new MyMembershipWithdrawalResponseDTO();
        myMembership.withdrawal(LocalDateTime.now());
        response.entityToDto(myMembership);

        return response;
    }

}
