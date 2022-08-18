package com.franc.app.code.converter;

import com.franc.app.code.AccountGrade;

import javax.persistence.Converter;

@Converter
public class AccountGradeConverter extends CodeValueConverter<AccountGrade> {
    AccountGradeConverter() {
        super(AccountGrade.class);
    }
}
