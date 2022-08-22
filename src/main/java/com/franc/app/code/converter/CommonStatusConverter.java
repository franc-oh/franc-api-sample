package com.franc.app.code.converter;

import com.franc.app.code.CommonStatus;
import javax.persistence.Converter;

@Converter
public class CommonStatusConverter extends CodeValueConverter<CommonStatus> {

    public CommonStatusConverter() {
        super(CommonStatus.class);
    }
}
