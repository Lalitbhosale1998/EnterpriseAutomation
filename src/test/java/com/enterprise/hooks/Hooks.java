package com.enterprise.hooks;

import com.enterprise.driver.DriverFactory;
import com.enterprise.driver.DriverManager;
import com.enterprise.utils.ExcelUtil;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.SkipException;

import java.util.Map;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        String scenarioName = scenario.getName();

        // 🛡️ Pre-filter: Check Excel BEFORE launching browser
        if (scenario.getSourceTagNames().contains("@datadriven")) {
            // Extract the last word/token (preserves 'Login' in 'ValidLoginTest')
            String[] parts = scenarioName.trim().split("\\s+");
            String testCaseName = parts[parts.length - 1].replace("-", "").trim();

            Map<String, String> data = ExcelUtil.getTestCaseData("LoginData", testCaseName);

            if (data.isEmpty()) {
                throw new SkipException("Test case '" + testCaseName + "' is marked 'N' in Excel. Skipping without opening browser!");
            }
        }

        // Only launches Chrome if the test was marked 'Y'!
        DriverFactory.initDriver();
    }

    @AfterStep
    public void captureStepScreenshot(Scenario scenario) {
        if (DriverManager.getDriver() != null) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);

            if (scenario.isFailed()) {
                scenario.attach(screenshot, "image/png", "Failed Step Screenshot");
            } else {
                scenario.attach(screenshot, "image/png", "Passed Step Screenshot");
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {}
        }
    }

    @After
    public void tearDown() {
        // Only wait and quit if a browser was actually opened
        if (DriverManager.getDriver() != null) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
            DriverManager.quitDriver();
        }
    }
}