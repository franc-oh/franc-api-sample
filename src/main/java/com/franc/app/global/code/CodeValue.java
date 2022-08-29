package com.franc.app.global.code;

import com.fasterxml.jackson.annotation.JsonValue;

public interface CodeValue {

    @JsonValue
    String getCode();
    String getValue();

}
