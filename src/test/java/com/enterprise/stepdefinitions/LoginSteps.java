package com.enterprise.stepdefinitions;

import com.enterprise.pages.DashboardPage;
import com.enterprise.pages.LoginPage;
import com.enterprise.utils.ExcelUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import org.testng.SkipException;

import java.util.Map;

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

    @Then("an error message should contain {string}")
    public void errorMessageShouldContain(String partialError) {
        Assert.assertTrue(loginPage.getErrorMessage().contains(partialError),
                "Expected error message to contain: " + partialError);
    }

    @And("the user should remain on the login page")
    public void userShouldRemainOnLoginPage() {
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button is not displayed, user did not remain on login page!");
    }

    @When("the user logs in using test data from sheet {string} for case {string}")
    public void loginUsingExcelData(String sheetName, String testCaseName) {
        Map<String, String> data = ExcelUtil.getTestCaseData(sheetName, testCaseName);

        // If Column A was 'N', ExcelUtil returns an empty map
        if (data.isEmpty()) {
            throw new SkipException("Test case '" + testCaseName + "' is marked 'N' in Excel. Skipping execution!");
        }

        loginPage.enterUsername(data.get("Username"));
        loginPage.enterPassword(data.get("Password"));
        loginPage.clickLogin();
    }
    @Then("the login result should match expected test data from sheet {string} for case {string}")
    public void verifyExcelLoginResult(String sheetName, String testCaseName) {
        Map<String, String> data = ExcelUtil.getTestCaseData(sheetName, testCaseName);
        String expected = data.get("ExpectedResult");

        // If Excel expects "Menu", open hamburger menu and verify sidebar is visible!
        if (expected.equalsIgnoreCase("Menu")) {
            dashboardPage.clickHamburgerMenuButton();
            Assert.assertTrue(dashboardPage.isAllItemsOptionDisplayed(), "Side menu is not displayed!");
        }
        else if (dashboardPage.isDashboardDisplayed()) {
            Assert.assertEquals(dashboardPage.getHeaderTitle(), expected);
        }
        else {
            Assert.assertTrue(loginPage.getErrorMessage().contains(expected),
                    "Expected error to contain: " + expected);
        }
    }

}
