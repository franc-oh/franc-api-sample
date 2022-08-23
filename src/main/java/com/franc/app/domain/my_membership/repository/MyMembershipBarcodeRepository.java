package com.franc.app.domain.my_membership.repository;

import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipBarcodeKey;
import com.franc.app.domain.my_membership.repository.entity.MyMembershipBarcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMembershipBarcodeRepository extends JpaRepository<MyMembershipBarcode, MyMembershipBarcodeKey> {
}
