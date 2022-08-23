package com.franc.app.global.code.converter;

import com.franc.app.global.code.CommonStatus;
import javax.persistence.Converter;

@Converter
public class CommonStatusConverter extends CodeValueConverter<CommonStatus> {

    public CommonStatusConverter() {
        super(CommonStatus.class);
    }
}
