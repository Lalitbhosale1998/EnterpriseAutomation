package com.enterprise.pages;

import org.openqa.selenium.By;

public class DashboardPage extends BasePage {

    // Locators for the post-login inventory dashboard
    private final By headerTitle = By.className("title");
    private final By appLogo     = By.className("app_logo");

    public boolean isDashboardDisplayed() {
        return isDisplayed(headerTitle) && isDisplayed(appLogo);
    }

    public String getHeaderTitle() {
        return getText(headerTitle);
    }
}