package com.franc.app.my_membership.repository;

import com.franc.app.domain.my_membership.repository.MyMembershipBarcodeRepository;
import com.franc.app.domain.my_membership.repository.entity.MyMembershipBarcode;
import com.franc.app.domain.my_membership.repository.entity.key.MyMembershipBarcodeKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyMembershipBarcodeRepositoryTest {

    @Autowired
    private MyMembershipBarcodeRepository myMembershipBarcodeRepository;

    @Test
    public void 멤버십적립바코드생성() throws Exception {
        // #given
        String hphone = "01023267971";
        String serviceFg = "01";


        // #when
        String barcode = getMyMembershipBarcodeNo(hphone, serviceFg);

        // #then
        assertThat(barcode).isNotEmpty();
        assertThat(barcode.length()).isEqualTo(18);
    }

    // 바코드번호 발급
    public String getMyMembershipBarcodeNo(String hphone, String serviceFg) {
        return myMembershipBarcodeRepository.getMyMembershipBarcodeNo(hphone, serviceFg);
    }

    @Test
    public void 멤버십적립바코드_생성_고객이바코드활성화() throws Exception {
        // #given
        Long mspId = 1L;
        Long accountId = 2L;
        String hphone = "01023267971";
        String serviceFg = "01";
        String barcode = getMyMembershipBarcodeNo(hphone, serviceFg);
        LocalDateTime createDate = LocalDateTime.now();

        // #when
        save(new MyMembershipBarcode().builder()
                                .barCd(barcode)
                                .createDate(createDate)
                                .mspId(mspId)
                                .accountId(accountId)
                                .expireDate(LocalDateTime.now().plusMinutes(5))
                                .tradeAmt(0)
                                .usePoint(0)
                                .build()
                        );

        MyMembershipBarcode myMembershipBarcode =
                myMembershipBarcodeRepository.findById(new MyMembershipBarcodeKey().builder()
                        .barCd(barcode)
                        .createDate(createDate)
                        .build()).orElse(null);

        // #then
        assertThat(myMembershipBarcode).isNotNull();
        assertThat(myMembershipBarcode.getMspId()).isEqualTo(mspId);
        assertThat(myMembershipBarcode.getAccountId()).isEqualTo(accountId);
        assertThat(myMembershipBarcode.getBarCd()).isEqualTo(barcode);
        assertThat(myMembershipBarcode.getCreateDate()).isNotNull();
        assertThat(myMembershipBarcode.getExpireDate()).isAfterOrEqualTo(createDate);

    }

    @Test
    public void 멤버십적립바코드_갱신_가맹점이바코드스캔() throws Exception {
        // #given
        Long mspId = 1L;
        Long accountId = 2L;
        Long franchisseId = 2L;
        String hphone = "01023267971";
        String serviceFg = "01";
        String barcode = getMyMembershipBarcodeNo(hphone, serviceFg);
        LocalDateTime createDate = LocalDateTime.now();
        Integer tradeAmt = 15000;
        Integer usePoint = 0;

        MyMembershipBarcode myMembershipBarcode = save(new MyMembershipBarcode().builder()
                .barCd(barcode)
                .createDate(createDate)
                .mspId(mspId)
                .accountId(accountId)
                .expireDate(LocalDateTime.now().plusMinutes(5))
                .tradeAmt(0)
                .usePoint(0)
                .build()
        );

        // #when
        myMembershipBarcode.useBarcode(franchisseId, tradeAmt, usePoint);
        myMembershipBarcodeRepository.flush();

        System.out.println("===============> " + myMembershipBarcode.toString());

        // #then
        assertThat(myMembershipBarcode.getTradeAmt()).isEqualTo(tradeAmt);
        assertThat(myMembershipBarcode.getUseDate()).isNotNull();
        assertThat(myMembershipBarcode.getFranchiseeId()).isEqualTo(franchisseId);







    }
    
    
    // 바코드 정보 저장
    @Transactional
    public MyMembershipBarcode save(MyMembershipBarcode myMembershipBarcode) throws Exception {
        return myMembershipBarcodeRepository.save(myMembershipBarcode);
    }


}
