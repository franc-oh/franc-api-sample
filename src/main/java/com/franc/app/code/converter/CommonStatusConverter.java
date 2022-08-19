package com.franc.app.code.converter;

import com.franc.app.code.CommonStatus;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Objects;

@Converter
public class CommonStatusConverter implements AttributeConverter<CommonStatus, String> {
    @Override
    public String convertToDatabaseColumn(CommonStatus attribute) {
        if(Objects.isNull(attribute)) return null;
        return attribute.getCode();
    }

    @Override
    public CommonStatus convertToEntityAttribute(String dbData) {
        if(StringUtils.isEmpty(dbData)) return null;
        return EnumSet.allOf(CommonStatus.class).stream()
                .filter(e -> e.getCode().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException());
    }
}
