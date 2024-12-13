package com.hyw.platform.web.syswebconfig;

import java.util.Optional;

public class TraceContext {
    public TraceContext() {
    }

    public static String traceId() {
        return "";
    }

    public static String segmentId() {
        return "";
    }

    public static int spanId() {
        return -1;
    }

    public static Optional<String> getCorrelation(String key) {
        return Optional.empty();
    }

    public static Optional<String> putCorrelation(String key, String value) {
        return Optional.empty();
    }
}
