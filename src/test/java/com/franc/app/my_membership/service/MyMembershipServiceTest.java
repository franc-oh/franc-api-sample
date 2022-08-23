package com.franc.app.my_membership.service;

import com.franc.app.domain.my_membership.service.MyMembershipService;
import com.franc.app.global.code.AccountGrade;
import com.franc.app.global.code.MyMembershipStatus;
import com.franc.app.domain.membership.repository.MembershipGradeRepository;
import com.franc.app.domain.membership.repository.entity.MembershipGrade;
import com.franc.app.domain.membership.repository.entity.key.MembershipGradeKey;
import com.franc.app.domain.my_membership.repository.MyMembershipRepository;
import com.franc.app.domain.my_membership.repository.entity.MyMembership;
import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipKey;
import com.franc.app.domain.my_membership.vo.MyMembershipReqDTO;
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
        MyMembershipReqDTO myMembershipReqDTO = buildReqDTO(accountId, mspId, accountGrade);

        // 사용자의 등급으로 가입이 가능한지 조회 => 데이터가 없다면??
        when(membershipGradeRepository.findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class)))
                .thenReturn(Optional.empty());

        // #when
        Exception result =
                assertThrows(Exception.class, () -> myMembershipService.join(myMembershipReqDTO));

        // #then
        assertThat(result.getMessage()).isEqualTo("해당 멤버십에 가입 불가능한 등급입니다.");
        verify(membershipGradeRepository, times(1)).findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class));
    }

    @Test
    public void 멤버십가입_실패_기등록인경우() throws Exception {
        // #given
        Long accountId = 2L; // 'USER' 등급의 사용자
        Long mspId = 1L; // USER 계급도 가입 가능한 멤버십
        AccountGrade accountGrade = AccountGrade._USER;
        MyMembershipStatus status = MyMembershipStatus.NON_USING;
        MyMembershipReqDTO myMembershipReqDTO = buildReqDTO(accountId, mspId, accountGrade);

        // 사용자의 등급으로 가입이 가능한지 조회 => 통과하도록 처리
        when(membershipGradeRepository.findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class)))
                .thenReturn(Optional.of(new MembershipGrade()));

        // 이미 가입한 멤버십인지 조회 => '사용' 또는 '중지' 상태의 멤버십이 있는 경우 (이미등록된멤버십)
        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                    .thenReturn(Optional.of(new MyMembership().builder()
                            .accountId(accountId)
                            .mspId(mspId)
                            .status(status)
                            .build()));

        // #when
        Exception result =
                assertThrows(Exception.class, () -> myMembershipService.join(myMembershipReqDTO));

        // #then
        // TODO : [리팩토링] - CustomException
        assertThat(result.getMessage()).isEqualTo("이미 가입한 멤버십입니다.");
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
        MyMembershipReqDTO myMembershipReqDTO = buildReqDTO(accountId, mspId, accountGrade);

        // 사용자의 등급으로 가입이 가능한지 조회 => 통과하도록 처리
        when(membershipGradeRepository.findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class)))
                .thenReturn(Optional.of(new MembershipGrade()));

        // 이미 가입한 멤버십인지 조회 => '탈퇴'상태의 멤버십 조회 (탈퇴당일인경우 재가입불가)
        when(myMembershipRepository.findById(any(MyMembershipKey.class)))
                .thenReturn(Optional.of(new MyMembership().builder()
                        .accountId(accountId)
                        .mspId(mspId)
                        .status(status)
                        .withdrawalDate(withdrawalDate)
                        .build()));

        // #when
        Exception result =
                assertThrows(Exception.class, () -> myMembershipService.join(myMembershipReqDTO));

        // #then
        assertThat(result.getMessage()).isEqualTo("탈퇴당일에는 재가입이 불가합니다.");
        verify(membershipGradeRepository, times(1)).findByIdAndStatusEqualsUsing(any(MembershipGradeKey.class));
        verify(myMembershipRepository, times(1)).findById(any(MyMembershipKey.class));
    }

    @Test
    public void 멤버십가입_성공_신규가입() throws Exception {

    }

    @Test
    public void 멤버십가입_성공_재가입() throws Exception {

    }

    public MyMembershipReqDTO buildReqDTO(Long accountId, Long mspId, AccountGrade accountGrade) {
        return new MyMembershipReqDTO().builder()
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

}
