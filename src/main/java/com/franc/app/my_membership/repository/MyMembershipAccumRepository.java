package com.franc.app.my_membership.repository;

import com.franc.app.my_membership.repository.entity.MyMembershipAccum;
import com.franc.app.my_membership.repository.entity.key.MyMembershipAccumKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMembershipAccumRepository extends JpaRepository<MyMembershipAccum, MyMembershipAccumKey> {
}
