package com.franc.app.global.code.converter;

import com.franc.app.global.code.MyMembershipStatus;

import javax.persistence.Converter;

@Converter
public class MyMembershipStatusConverter extends CodeValueConverter<MyMembershipStatus> {

    public MyMembershipStatusConverter() {
        super(MyMembershipStatus.class);
    }
}
