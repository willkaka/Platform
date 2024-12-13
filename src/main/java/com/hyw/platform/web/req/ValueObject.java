package com.hyw.platform.web.req;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ValueObject{
    private Object value;
    private Object defValue;//原值
}

