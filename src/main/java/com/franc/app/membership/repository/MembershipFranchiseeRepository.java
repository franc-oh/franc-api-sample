package com.franc.app.membership.repository;

import com.franc.app.membership.repository.entity.MembershipFranchisee;
import com.franc.app.membership.repository.entity.key.MembershipFranchiseeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipFranchiseeRepository extends JpaRepository<MembershipFranchisee, MembershipFranchiseeKey> {
}
