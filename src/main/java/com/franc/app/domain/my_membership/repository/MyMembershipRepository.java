package com.franc.app.domain.my_membership.repository;

import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipKey;
import com.franc.app.domain.my_membership.repository.entity.MyMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMembershipRepository extends JpaRepository<MyMembership, MyMembershipKey> {
}
