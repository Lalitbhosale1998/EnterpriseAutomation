package com.enterprise.utils;

import com.enterprise.constants.FrameworkConstants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties properties = new Properties();

    // Static block runs once when the class is loaded to read the file into memory
    static {
        try (FileInputStream fileInputStream = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file at: " + FrameworkConstants.CONFIG_FILE_PATH, e);
        }
    }

    private ConfigReader() {
        // Prevent instantiation
    }

    /**z
     * Gets property value from config.properties or checks System properties (-D flags)
     */
    public static String getProperty(String key) {
        // First check if passed via Maven CLI (e.g. -Dbrowser=edge)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.trim().isEmpty()) {
            return systemProperty.trim();
        }

        String value = properties.getProperty(key);
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties!");
        }
        return value.trim();
    }
}