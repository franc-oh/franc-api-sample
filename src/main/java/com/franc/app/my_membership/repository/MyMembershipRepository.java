package com.franc.app.my_membership.repository;

import com.franc.app.my_membership.repository.entity.MyMembership;
import com.franc.app.my_membership.repository.entity.key.MyMembershipKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMembershipRepository extends JpaRepository<MyMembership, MyMembershipKey> {
}
