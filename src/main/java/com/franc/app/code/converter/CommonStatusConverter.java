package com.franc.app.code.converter;

import com.franc.app.code.CommonStatus;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class CommonStatusConverter extends CodeValueConverter<CommonStatus> {
    CommonStatusConverter() {
        super(CommonStatus.class);
    }
}
