package com.franc.app.domain.my_membership.repository;

import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipBarcodeKey;
import com.franc.app.domain.my_membership.repository.entity.MyMembershipBarcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MyMembershipBarcodeRepository extends JpaRepository<MyMembershipBarcode, MyMembershipBarcodeKey> {

    @Query(value = "SELECT MY_MEMBERSHIP_BARCODE_SEQ.nextval FROM DUAL", nativeQuery = true)
    Integer myMembershipBarcodeSeqNextVal();

}
