package com.franc.app.global.code.converter;

import com.franc.app.global.code.CodeValue;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Objects;

@AllArgsConstructor
public class CodeValueConverter <E extends Enum<E> & CodeValue> implements AttributeConverter<E, String> {

    private Class<E> clz;


    @Override
    public String convertToDatabaseColumn(E attribute) {
        if(Objects.isNull(attribute)) return null;
        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        if(StringUtils.isEmpty(dbData)) return null;
        return EnumSet.allOf(clz).stream()
                .filter(e->e.getCode().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException());
    }
}
