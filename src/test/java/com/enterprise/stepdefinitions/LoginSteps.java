package com.enterprise.stepdefinitions;

import com.enterprise.pages.DashboardPage;
import com.enterprise.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    @Given("the user is on the login page")
    public void userIsOnLoginPage() {
        loginPage.navigateToLoginPage();
    }

    @When("the user enters valid username {string} and password {string}")
    public void userEntersValidCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the user enters invalid username {string} and password {string}")
    public void userEntersInvalidCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @And("clicks the login button")
    public void clicksLoginButton() {
        loginPage.clickLogin();
    }

    @Then("the user should be redirected to the dashboard")
    public void userRedirectedToDashboard() {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard was not displayed!");
    }

    @And("the header title should display {string}")
    public void headerTitleShouldDisplay(String expectedTitle) {
        Assert.assertEquals(dashboardPage.getHeaderTitle(), expectedTitle, "Dashboard header title does not match!");
    }

    @Then("an error message should contain {string}")
    public void errorMessageShouldContain(String partialError) {
        Assert.assertTrue(loginPage.getErrorMessage().contains(partialError),
                "Expected error message to contain: " + partialError);
    }

    @And("the user should remain on the login page")
    public void userShouldRemainOnLoginPage() {
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed, user did not remain on login page!");
    }
}