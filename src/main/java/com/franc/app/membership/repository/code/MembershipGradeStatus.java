package com.franc.app.membership.repository.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * [코드] 멤버십 등급_상태 (1:사용 9:사용금지)
 */
@Getter
@RequiredArgsConstructor
public enum MembershipGradeStatus {
    USING("1"),
    NON_USING("9");

    private final String membershipGradeStatus;
}
