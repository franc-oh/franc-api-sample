package com.franc.app.domain.my_membership.repository.entity.key;

import javax.persistence.Id;
import java.io.Serializable;

public class MyMembershipAccumKey implements Serializable {
    private String pointAccumSeq;
    private Long mspId;
    private Long accountId;
}
