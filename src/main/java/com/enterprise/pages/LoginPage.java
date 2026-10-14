package com.enterprise.pages;

import com.enterprise.utils.ConfigReader;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // 1. Locators stored as private By variables
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("[data-test='error']");

    // 2. Page Actions
    public void navigateToLoginPage() {
        com.enterprise.driver.DriverManager.getDriver().get(ConfigReader.getProperty("url"));
    }

    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }

    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }

    public void clickLogin() {
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isLoginButtonDisplayed() {
        return isDisplayed(loginButton);
    }
}