package com.hyw.platform.config;

import org.springframework.stereotype.Service;

@Service
public class DataSourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDataSource(String dsKey) {
        CONTEXT_HOLDER.set(dsKey);
    }

    public static String getDataSource() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
