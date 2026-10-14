package com.enterprise.pages;

import com.enterprise.driver.DriverManager;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardPage extends BasePage {

    // Locators for the post-login inventory dashboard
    private final By headerTitle = By.className("title");
    private final By appLogo     = By.className("app_logo");
    private final By productTitles = By.className("inventory_item_name");
    private final By hamburgerMenuButton   = By.id("react-burger-menu-btn");
    private final By allItemsLink = By.id("inventory_sidebar_link");

    public boolean isDashboardDisplayed() {
        return isDisplayed(headerTitle) && isDisplayed(appLogo);
    }

    public String getHeaderTitle() {
        return getText(headerTitle);
    }
    public void clickHamburgerMenuButton() {
        click(hamburgerMenuButton);
    }
    public boolean isAllItemsOptionDisplayed(){
        return isDisplayed(allItemsLink);
    }
    /**
     * Finds all product elements on the page and extracts their text
     */
    public List<String> getAllProductNames() {
        wait.until(driver -> !driver.findElements(productTitles).isEmpty());
        List<WebElement> productElements = DriverManager.getDriver().findElements(productTitles);
        return productElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

}