package com.franc.app.domain.my_membership.repository.entity.key;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyMembershipBarcodeKey implements Serializable {
    private String barCd;
    private LocalDateTime createDate;
}
