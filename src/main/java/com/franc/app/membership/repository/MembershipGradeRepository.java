package com.franc.app.membership.repository;

import com.franc.app.membership.repository.entity.MembershipGrade;
import com.franc.app.membership.repository.entity.key.MembershipGradeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MembershipGradeRepository extends JpaRepository<MembershipGrade, MembershipGradeKey> {
    @Query(value = "select mg from MembershipGrade mg "
            + "where mg.id.mspId = :#{#membershipGradeKey.mspId} "
            + "and mg.id.accountGrade = :#{#membershipGradeKey.accountGrade} "
            + "and mg.status = '1'"
    )
    MembershipGrade findByIdAndStatusEqualsUsing(@Param("membershipGradeKey") MembershipGradeKey membershipGradeKey);
}
