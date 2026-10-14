package com.enterprise.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    // ThreadLocal ensures every thread maintains its own independent WebDriver instance
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Prevent instantiation
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove(); // Vital: removes the reference to avoid memory leaks
        }
    }
}