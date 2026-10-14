package com.enterprise.constants;

import java.time.Duration;

public final class FrameworkConstants {

    private FrameworkConstants() {
        // Private constructor prevents instantiation
    }

    public static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";
    public static final Duration EXPLICIT_WAIT_TIMEOUT = Duration.ofSeconds(10);
}