package com.franc.app.code.converter;

import com.franc.app.code.MyMembershipStatus;

import javax.persistence.Converter;

@Converter
public class MyMembershipStatusConverter extends CodeValueConverter<MyMembershipStatus> {

    public MyMembershipStatusConverter() {
        super(MyMembershipStatus.class);
    }
}
