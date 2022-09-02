package com.franc.app.my_membership.repository;

import com.franc.app.domain.my_membership.repository.MyMembershipBarcodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyMembershipBarcodeRepositoryTest {

    @Autowired
    private MyMembershipBarcodeRepository myMembershipBarcodeRepository;

    @Test
    public void 멤버십바코드생성() throws Exception {
        // #given
        Long accountId = 2L;
        String serviceFg = "01";

        // #when
        String barcode = myMembershipBarcodeRepository.getMyMembershipBarcodeNo(accountId, serviceFg);

        System.out.println("barcode : " + barcode);

        // #then
        assertThat(barcode).isNotEmpty();
        assertThat(barcode.length()).isEqualTo(18);
    }


}
