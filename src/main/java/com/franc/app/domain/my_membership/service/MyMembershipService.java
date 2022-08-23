package com.franc.app.domain.my_membership.service;

import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipKey;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.domain.membership.repository.MembershipGradeRepository;
import com.franc.app.domain.membership.repository.entity.MembershipGrade;
import com.franc.app.domain.membership.repository.entity.key.MembershipGradeKey;
import com.franc.app.domain.my_membership.repository.MyMembershipRepository;
import com.franc.app.domain.my_membership.repository.entity.MyMembership;
import com.franc.app.domain.my_membership.vo.MyMembershipReqDTO;
import com.franc.app.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MyMembershipService {

    private final MembershipGradeRepository membershipGradeRepository;
    private final MyMembershipRepository myMembershipRepository;

    /**
     * 멤버십 가입
     * @param req
     * @return
     * @throws Exception
     */
    public MyMembership join(MyMembershipReqDTO req) throws Exception {

        // #1. 가입가능한 멤버십인지 -- 가입등급 체크
        MembershipGrade availMembershipGrade =
                membershipGradeRepository.findByIdAndStatusEqualsUsing(new MembershipGradeKey().builder()
                        .accountGrade(req.getAccountGrade())
                        .mspId(req.getMspId())
                        .build())
                .orElse(null);

        if(availMembershipGrade == null)
            throw new Exception("해당 멤버십에 가입 불가능한 등급입니다.");

        log.debug("멤버십가입_가입등급체크 => " + availMembershipGrade.toString());

        // #2. 기등록여부 체크
        MyMembership existMyMembership = myMembershipRepository.findById(new MyMembershipKey().builder()
                        .accountId(req.getAccountId())
                        .mspId(req.getMspId())
                        .build())
                .orElse(null);

        log.debug("멤버십가입_기등록여부체크 => " + existMyMembership == null ? "{내역없음_신규가입}" : existMyMembership.toString());

        MyMembershipStatus existMembershipStatus = null;
        LocalDateTime withdrawalDate = null;
        if(existMyMembership != null) {
            existMembershipStatus = existMyMembership.getStatus();
            withdrawalDate = existMyMembership.getWithdrawalDate();
        }

        // ##2-1. '사용' 또는 '중지' 상태의 멤버십인 경우 => 기등록 멤버십
        if(MyMembershipStatus.USING.equals(existMembershipStatus) || MyMembershipStatus.NON_USING.equals(existMembershipStatus))
            throw new Exception("이미 가입한 멤버십입니다.");

        // ##2-2. '탈퇴'상태의 멤버십인 경우 => 탈퇴당일 재가입 불가
        if(MyMembershipStatus.WITHDRAWAL.equals(existMembershipStatus)
                && DateUtil.isEqualNowDate(withdrawalDate))
            throw new Exception("탈퇴당일에는 재가입이 불가합니다.");





        return null;
    }

}
