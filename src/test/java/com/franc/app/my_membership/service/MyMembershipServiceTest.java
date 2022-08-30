package com.franc.app.my_membership.service;

import com.franc.app.domain.my_membership.dto.MyMembershipWithdrawalRequestDTO;
import com.franc.app.domain.my_membership.dto.MyMembershipWithdrawalResponseDTO;
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

import java.time.LocalDate;
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
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildJoinReqDTO(accountId, mspId, accountGrade);

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
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildJoinReqDTO(accountId, mspId, accountGrade);

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
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildJoinReqDTO(accountId, mspId, accountGrade);

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
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildJoinReqDTO(accountId, mspId, accountGrade);
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
        MyMembershipJoinRequestDTO myMembershipReqDTO = buildJoinReqDTO(accountId, mspId, accountGrade);
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

    @Test
    public void 멤버십탈퇴_실패_멤버십가입정보가없는경우() throws Exception {
        // #given
        Long accountId = 2L;
        Long mspId = 1L;

        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                .thenReturn(Optional.empty());

        // #when
        BizException exception
                = assertThrows(BizException.class, () -> myMembershipService.withdrawal(buildWithdrawalReqDTO(accountId, mspId)));

        // #then
        assertThat(exception.getResult()).isEqualTo(GlobalExceptionResult.MY_MEMBERSHIP_WITHDRAWAL_NOT_EXIST_MEMBERSHIP);
        verify(myMembershipRepository, times(1)).findById(any(MyMembershipKey.class));

    }
    @Test
    public void 멤버십탈퇴_실패_탈퇴불가능한상태() throws Exception {
        // #given
        Long accountId = 2L;
        Long mspId = 1L;

        // 상태값이 '사용' 중이 아닌 경우 탈퇴불가
        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                .thenReturn(Optional.of(new MyMembership().builder()
                        .accountId(accountId)
                        .mspId(mspId)
                        .status(MyMembershipStatus.NON_USING)
                        .build()));

        // #when
        BizException exception
                = assertThrows(BizException.class, () -> myMembershipService.withdrawal(buildWithdrawalReqDTO(accountId, mspId)));

        // #then
        assertThat(exception.getResult()).isEqualTo(GlobalExceptionResult.MY_MEMBERSHIP_WITHDRAWAL_NOT_AVAIL_STATUS);
        verify(myMembershipRepository, times(1)).findById(any(MyMembershipKey.class));
    }

    @Test
    public void 멤버십탈퇴_성공() throws Exception {
        // #given
        Long accountId = 2L;
        Long mspId = 1L;
        LocalDate withdrawalDate8 = LocalDateTime.now().toLocalDate();

        // 탈퇴를 위해 '사용' 중인 멤버십으로 셋팅
        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                .thenReturn(Optional.of(new MyMembership().builder()
                        .accountId(accountId)
                        .mspId(mspId)
                        .status(MyMembershipStatus.USING)
                        .totalAmt(0)
                        .usablePoint(0)
                        .build()));

        // #when
        MyMembershipWithdrawalResponseDTO withdrawalMembership = myMembershipService.withdrawal(buildWithdrawalReqDTO(accountId, mspId));

        // #then
        assertThat(withdrawalMembership).isNotNull();
        assertThat(withdrawalMembership.getAccountId()).isEqualTo(accountId);
        assertThat(withdrawalMembership.getMspId()).isEqualTo(mspId);
        assertThat(withdrawalMembership.getStatus()).isEqualTo(MyMembershipStatus.WITHDRAWAL);
        assertThat(withdrawalMembership.getWithdrawalDate().toLocalDate()).isEqualTo(withdrawalDate8);
        verify(myMembershipRepository, times(1)).findById(any(MyMembershipKey.class));
    }



    public MyMembershipJoinRequestDTO buildJoinReqDTO(Long accountId, Long mspId, AccountGrade accountGrade) {
        return new MyMembershipJoinRequestDTO().builder()
                .accountId(accountId)
                .mspId(mspId)
                .accountGrade(accountGrade)
                .build();
    }

    public MyMembershipWithdrawalRequestDTO buildWithdrawalReqDTO(Long accountId, Long mspId) {
        return new MyMembershipWithdrawalRequestDTO().builder()
                .accountId(accountId)
                .mspId(mspId)
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
