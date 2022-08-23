package com.franc.app.domain.membership.repository;

import com.franc.app.domain.membership.repository.entity.key.MembershipFranchiseeKey;
import com.franc.app.domain.membership.repository.entity.MembershipFranchisee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipFranchiseeRepository extends JpaRepository<MembershipFranchisee, MembershipFranchiseeKey> {
}
