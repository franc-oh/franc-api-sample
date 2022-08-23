package com.franc.app.domain.my_membership.repository;

import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipAccumKey;
import com.franc.app.domain.my_membership.repository.entity.MyMembershipAccum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMembershipAccumRepository extends JpaRepository<MyMembershipAccum, MyMembershipAccumKey> {
}
