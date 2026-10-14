package com.enterprise.pages;

import com.enterprise.constants.FrameworkConstants;
import com.enterprise.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    protected WebDriverWait wait;

    public BasePage() {
        this.wait = new WebDriverWait(DriverManager.getDriver(), FrameworkConstants.EXPLICIT_WAIT_TIMEOUT);
    }

    /**
     * Waits for element to be clickable before clicking
     */
    protected void click(By by) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        element.click();
    }

    /**
     * Waits for element to be visible, clears it, and types the text
     */
    protected void sendKeys(By by, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Waits for element to be visible and returns its text
     */
    protected String getText(By by) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element.getText();
    }

    /**
     * Checks if element is visible on the page
     */
    protected boolean isDisplayed(By by) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}