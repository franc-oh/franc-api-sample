package com.franc.app.membership.repository;

import com.franc.app.membership.repository.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
