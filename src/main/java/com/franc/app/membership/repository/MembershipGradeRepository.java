package com.franc.app.membership.repository;

import com.franc.app.membership.repository.entity.MembershipGrade;
import com.franc.app.membership.repository.entity.key.MembershipGradeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipGradeRepository extends JpaRepository<MembershipGrade, MembershipGradeKey> {
}
