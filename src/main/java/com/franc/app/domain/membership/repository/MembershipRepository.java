package com.franc.app.domain.membership.repository;

import com.franc.app.domain.membership.repository.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
