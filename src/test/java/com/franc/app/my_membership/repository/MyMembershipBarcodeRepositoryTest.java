package com.franc.app.my_membership.repository;

import com.franc.app.domain.my_membership.repository.MyMembershipBarcodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MyMembershipBarcodeRepositoryTest {

    @Autowired
    private MyMembershipBarcodeRepository myMembershipBarcodeRepository;




}
