package com.franc.app.code.converter;

import com.franc.app.code.CodeValue;
import lombok.AllArgsConstructor;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class CodeValueConverter <E extends Enum<E> & CodeValue> implements AttributeConverter<E, String> {

    private Class<E> clz;


    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute.getCode();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return EnumSet.allOf(clz).stream()
                .filter(e->e.getCode().equals(dbData))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException());
    }
}
