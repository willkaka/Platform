package com.hyw.platform.web.util;

import com.ql.util.express.IExpressContext;

import java.util.HashMap;
import java.util.Map;

public class ExpressContext implements IExpressContext {

    private final Map<String, Object> map;

    public ExpressContext() {
        this.map = new HashMap<>();
    }

    @Override
    public Object get(Object key) {
        return this.map.get(key);
    }

    @Override
    public Object put(Object name, Object object) {
        return this.map.put((String) name, object);
    }

}
