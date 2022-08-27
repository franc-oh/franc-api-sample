package com.franc.app.my_membership.service;

import com.franc.app.domain.my_membership.service.MyMembershipService;
import com.franc.app.domain.my_membership.dto.MyMembershipJoinResponseDTO;
import com.franc.app.global.code.AccountGrade;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.domain.membership.repository.MembershipGradeRepository;
import com.franc.app.domain.membership.repository.entity.MembershipGrade;
import com.franc.app.domain.membership.repository.entity.key.MembershipGradeKey;
import com.franc.app.domain.my_membership.repository.MyMembershipRepository;
import com.franc.app.domain.my_membership.repository.entity.MyMembership;
import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipKey;
import com.franc.app.domain.my_membership.dto.MyMembershipJoinRequestDTO;
import com.franc.app.global.exception.BizException;
import com.franc.app.global.exception.GlobalExceptionResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MyMembershipServiceTest {

    @InjectMocks
    private MyMembershipService myMembershipService;
    @Mock
    private MyMembershipRepository myMembershipRepository;
    @Mock
    private MembershipGradeRepository membershipGradeRepository;



    @Test
    public void 멤버십가입_실패_가입가능등급이아닌경우() {
        // #given
        Long accountId = 2L; // 'USER' 등급의 사용자
        Long mspId = 3L; // VIP만 받는 멤버십
        AccountGrade accountGrade = AccountGrade._USER;
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildReqDTO(accountId, mspId, accountGrade);

        // 사용자의 등급으로 가입이 가능한지 조회 => 데이터가 없다면??
        when(membershipGradeRepository.findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class)))
                .thenReturn(Optional.empty());

        // #when
        BizException exception =
                assertThrows(BizException.class, () -> myMembershipService.join(myMembershipReqDTO));

        // #then
        assertThat(exception.getResult()).isEqualTo(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_NON_PERMISSION_GRADE);
        verify(membershipGradeRepository, times(1)).findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class));
    }

    @Test
    public void 멤버십가입_실패_기등록인경우() throws Exception {
        // #given
        Long accountId = 2L; // 'USER' 등급의 사용자
        Long mspId = 1L; // USER 계급도 가입 가능한 멤버십
        AccountGrade accountGrade = AccountGrade._USER;
        MyMembershipStatus status = MyMembershipStatus.NON_USING;
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildReqDTO(accountId, mspId, accountGrade);

        // 사용자의 등급으로 가입이 가능한지 조회 => 통과하도록 처리
        when(membershipGradeRepository.findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class)))
                .thenReturn(Optional.of(new MembershipGrade()));

        // 이미 가입한 멤버십인지 조회 => '사용' 또는 '중지' 상태의 멤버십이 있는 경우 (이미등록된멤버십)
        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                    .thenReturn(Optional.of(buildEntity(accountId, mspId, status, null)));

        // #when
        BizException exception =
                assertThrows(BizException.class, () -> myMembershipService.join(myMembershipReqDTO));

        // #then
        assertThat(exception.getResult()).isEqualTo(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_DUPLICATED_MEMBERSHIP);
        verify(membershipGradeRepository, times(1)).findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class));
        verify(myMembershipRepository, times(1)).findById(any(MyMembershipKey.class));
    }

    @Test
    public void 멤버십가입_실패_탈퇴당일가입인경우() throws Exception {
        // #given
        Long accountId = 2L; // 'USER' 등급의 사용자
        Long mspId = 1L; // USER 계급도 가입 가능한 멤버십
        AccountGrade accountGrade = AccountGrade._USER;
        MyMembershipStatus status = MyMembershipStatus.WITHDRAWAL;
        LocalDateTime withdrawalDate = LocalDateTime.now();
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildReqDTO(accountId, mspId, accountGrade);

        // 사용자의 등급으로 가입이 가능한지 조회 => 통과하도록 처리
        when(membershipGradeRepository.findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class)))
                .thenReturn(Optional.of(new MembershipGrade()));

        // 이미 가입한 멤버십인지 조회 => '탈퇴'상태의 멤버십 조회 (탈퇴당일인경우 재가입불가)
        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                .thenReturn(Optional.of(buildEntity(accountId, mspId, status, withdrawalDate)));

        // #when
        BizException exception =
                assertThrows(BizException.class, () -> myMembershipService.join(myMembershipReqDTO));

        // #then
        assertThat(exception.getResult()).isEqualTo(GlobalExceptionResult.MY_MEMBERSHIP_JOIN_NOT_AVAILABLE_REJOIN);
        verify(membershipGradeRepository, times(1)).findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class));
        verify(myMembershipRepository, times(1)).findById(any(MyMembershipKey.class));
    }

    @Test
    public void 멤버십가입_성공_신규가입() throws Exception {
        // #given
        Long accountId = 2L; // 'USER' 등급의 사용자
        Long mspId = 1L; // USER 계급도 가입 가능한 멤버십
        AccountGrade accountGrade = AccountGrade._USER;
        MyMembershipStatus status = MyMembershipStatus.USING;
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildReqDTO(accountId, mspId, accountGrade);
        MyMembership myMembership = buildEntity(accountId, mspId, status, null);

        // 사용자의 등급으로 가입이 가능한지 조회 => 통과하도록 처리
        when(membershipGradeRepository.findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class)))
                .thenReturn(Optional.of(new MembershipGrade()));

        // 이미 가입한 멤버십인지 조회 => X -> 신규가입 로직 처리
        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                .thenReturn(Optional.empty());

        // 신규가입처리
        when(myMembershipRepository.save(any(MyMembership.class)))
                .thenReturn(myMembership);

        // #when
        MyMembershipJoinResponseDTO joinMyMembership = myMembershipService.join(myMembershipReqDTO);

        // #then
        assertThat(joinMyMembership).isNotNull();
        assertThat(joinMyMembership.getAccountId()).isEqualTo(accountId);
        assertThat(joinMyMembership.getMspId()).isEqualTo(mspId);
        assertThat(joinMyMembership.getStatus()).isEqualTo(status);
        verify(membershipGradeRepository, times(1)).findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class));
        verify(myMembershipRepository, times(1)).findById(any(MyMembershipKey.class));
        verify(myMembershipRepository, times(1)).save(any(MyMembership.class));

    }

    @Test
    public void 멤버십가입_성공_재가입() throws Exception {
        // #given
        Long accountId = 2L; // 'USER' 등급의 사용자
        Long mspId = 1L; // USER 계급도 가입 가능한 멤버십
        AccountGrade accountGrade = AccountGrade._USER;
        MyMembershipStatus status = MyMembershipStatus.WITHDRAWAL;
        LocalDateTime withdrawalDate = LocalDateTime.now().minusDays(1L);
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildReqDTO(accountId, mspId, accountGrade);
        MyMembership myMembership = buildEntity(accountId, mspId, status, withdrawalDate);

        // 사용자의 등급으로 가입이 가능한지 조회 => 통과하도록 처리
        when(membershipGradeRepository.findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class)))
                .thenReturn(Optional.of(new MembershipGrade()));

        // 이미 가입한 멤버십인지 조회 => '탈퇴'상태의 멤버십 조회
        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                .thenReturn(Optional.of(myMembership));

        // #when
        MyMembershipJoinResponseDTO joinMyMembership = myMembershipService.join(myMembershipReqDTO);

        // #then
        assertThat(joinMyMembership).isNotNull();
        assertThat(joinMyMembership.getAccountId()).isEqualTo(accountId);
        assertThat(joinMyMembership.getMspId()).isEqualTo(mspId);
        assertThat(joinMyMembership.getStatus()).isEqualTo(MyMembershipStatus.USING);
        assertThat(joinMyMembership.getWithdrawalDate()).isNull();
        verify(membershipGradeRepository, times(1)).findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class));
        verify(myMembershipRepository, times(1)).findById(any(MyMembershipKey.class));
    }



    public MyMembershipJoinRequestDTO buildReqDTO(Long accountId, Long mspId, AccountGrade accountGrade) {
        return new MyMembershipJoinRequestDTO().builder()
                .accountId(accountId)
                .mspId(mspId)
                .accountGrade(accountGrade)
                .build();
    }

    public MyMembershipKey buildKey(Long accountId, Long mspId) {
        return new MyMembershipKey().builder()
                .accountId(accountId)
                .mspId(mspId)
                .build();
    }

    public MyMembership buildEntity(Long accountId, Long mspId, MyMembershipStatus status, LocalDateTime withdrawalDate) {
        return new MyMembership().builder()
                .accountId(accountId)
                .mspId(mspId)
                .status(status)
                .withdrawalDate(withdrawalDate)
                .build();
    }

}
