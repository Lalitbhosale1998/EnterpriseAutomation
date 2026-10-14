package com.enterprise.stepdefinitions;

import com.enterprise.pages.DashboardPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class DashboardSteps {

    private final DashboardPage dashboardPage = new DashboardPage();

    @Then("the user should be redirected to the dashboard")
    public void userRedirectedToDashboard() {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard was not displayed!");
    }

    @And("the header title should display {string}")
    public void headerTitleShouldDisplay(String expectedTitle) {
        Assert.assertEquals(dashboardPage.getHeaderTitle(), expectedTitle, "Dashboard header title does not match!");
    }

    @And("all product names should be retrieved and printed")
    public void retrieveAndPrintAllProducts() {
        List<String> products = dashboardPage.getAllProductNames();
        System.out.println("=========================================");
        System.out.println(" TOTAL PRODUCTS FOUND: " + products.size());
        System.out.println("=========================================");
        for (int i = 0; i < products.size(); i++) {
            System.out.println((i + 1) + ". " + products.get(i));
        }
        System.out.println("=========================================");
    }

    @And("the total number of products should be {int}")
    public void verifyProductCount(int expectedCount) {
        int actualCount = dashboardPage.getAllProductNames().size();
        Assert.assertEquals(actualCount, expectedCount, "Product count mismatch!");
    }

    @And("clicks the hamburger Menu Button")
    public void clicksHamburgerMenuButton() {
        dashboardPage.clickHamburgerMenuButton();
    }
}