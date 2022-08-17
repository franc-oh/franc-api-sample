package com.franc.app.my_membership.repository;

import com.franc.app.my_membership.repository.entity.MyMembershipBarcode;
import com.franc.app.my_membership.repository.entity.key.MyMembershipBarcodeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyMembershipBarcodeRepository extends JpaRepository<MyMembershipBarcode, MyMembershipBarcodeKey> {
}
