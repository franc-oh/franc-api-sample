package com.franc.app.domain.my_membership.repository.entity.key;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MyMembershipBarcodeKey implements Serializable {
    private String barCd;
    private LocalDateTime createDate;
}