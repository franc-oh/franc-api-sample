package com.franc.app.my_membership.repository.entity.key;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyMembershipKey implements Serializable {
    private Long accountId;
    private Long mspId;
}
