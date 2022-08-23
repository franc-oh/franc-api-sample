package com.franc.app.global.code.converter;

import com.franc.app.global.code.AccountGrade;
import javax.persistence.Converter;

@Converter
public class AccountGradeConverter extends CodeValueConverter<AccountGrade> {
    public AccountGradeConverter() {
        super(AccountGrade.class);
    }
}
