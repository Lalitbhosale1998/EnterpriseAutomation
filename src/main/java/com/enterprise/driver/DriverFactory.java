package com.enterprise.driver;

import com.enterprise.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public final class DriverFactory {

    private DriverFactory() {
        // Prevent instantiation
    }

    /**
     * Initializes the WebDriver based on config.properties (or Maven CLI flag)
     * Utilizes Selenium 4's built-in Selenium Manager to automatically handle driver binaries.
     */
    public static void initDriver() {
        if (DriverManager.getDriver() == null) {
            String browser = ConfigReader.getProperty("browser").toLowerCase();
            boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

            WebDriver driver;

            switch (browser) {
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (isHeadless) {
                        edgeOptions.addArguments("--headless=new");
                    }
                    edgeOptions.addArguments("--start-maximized");
                    edgeOptions.addArguments("--disable-notifications");
                    driver = new EdgeDriver(edgeOptions);
                    break;

                case "chrome":
                default:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless) {
                        chromeOptions.addArguments("--headless=new");
                    }
                    chromeOptions.addArguments("--start-maximized");
                    chromeOptions.addArguments("--disable-notifications");
                    driver = new ChromeDriver(chromeOptions);
                    break;
            }

            DriverManager.setDriver(driver);
        }
    }
}