package com.franc.app.domain.my_membership.repository;

import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipBarcodeKey;
import com.franc.app.domain.my_membership.repository.entity.MyMembershipBarcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyMembershipBarcodeRepository extends JpaRepository<MyMembershipBarcode, MyMembershipBarcodeKey> {

    @Query(value = "SELECT MY_MEMBERSHIP_BARCODE_SEQ.nextval FROM DUAL", nativeQuery = true)
    Integer myMembershipBarcodeSeqNextVal();


    // TODO : 바코드번호 채번 함수 만들기 (현재 임의로 채번 중)
    //@Query(value = "SELECT F_GET_MY_MEMBERSHIP_BARCODE_NO(:accountId, :serviceFg) FROM DUAL", nativeQuery = true)
    @Query(value = "SELECT "
            + "88 || SUBSTR(:hphone, -3) || '1'"
            + "|| TRIM(TO_CHAR(SYSTIMESTAMP, 'D')) || TRIM( TO_CHAR( TO_CHAR(SYSTIMESTAMP, 'HH')*60*60 + TO_CHAR(SYSTIMESTAMP, 'MM')*60 + TO_CHAR(SYSTIMESTAMP, 'SS') , '00000' ) ) || TRIM(TO_CHAR(MY_MEMBERSHIP_BARCODE_SEQ.NEXTVAL, '0000'))"
            + "|| NVL(:serviceFg, '00')"
            + "FROM DUAL", nativeQuery = true)
    String getMyMembershipBarcodeNo(@Param("hphone") String hphone, @Param("serviceFg") String serviceFg);

}
