package com.enterprise.hooks;

import com.enterprise.driver.DriverFactory;
import com.enterprise.driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp() {
        // Initializes Chrome or Edge based on config.properties before each scenario
        DriverFactory.initDriver();
    }

    /**
     * Executes immediately after EVERY single step (Given, When, Then).
     * Automatically captures a screenshot and embeds it inside the step!
     */
    @AfterStep
    public void captureStepScreenshot(Scenario scenario) {
        if (DriverManager.getDriver() != null) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);

            if (scenario.isFailed()) {
                scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
            } else {
                scenario.attach(screenshot, "image/png", "Passed Step Screenshot");
            }
        }
    }

    @After
    public void tearDown() {
        // Closes browser and removes the ThreadLocal reference
        DriverManager.quitDriver();
    }
}