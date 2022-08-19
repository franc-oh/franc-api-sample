package com.franc.app.code.converter;

import com.franc.app.code.AccountGrade;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Objects;

@Converter
public class AccountGradeConverter implements AttributeConverter<AccountGrade, String> {
    @Override
    public String convertToDatabaseColumn(AccountGrade attribute) {
        if(Objects.isNull(attribute)) return null;
        return attribute.getCode();
    }

    @Override
    public AccountGrade convertToEntityAttribute(String dbData) {
        if(StringUtils.isEmpty(dbData)) return null;
        return EnumSet.allOf(AccountGrade.class).stream()
                .filter(e -> e.getCode().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException());
    }
}
