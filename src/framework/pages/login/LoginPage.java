package framework.pages.login;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import framework.commonutils.Browser;
import framework.commonutils.DriverActions;
import framework.commonutils.DriverWaits;
import framework.pages.Pages;
import framework.pages.testdata.LoginCredentialsData;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * A page object class for 'Login' page.
 * Declared web elements on this page using page factory.
 *
 * @publicMethod
 * <p>
 * @use {@link #login(LoginCredentialsData)} method logs in to Facebook home page.
 * </p>
 *
 * @author jahangir
 *
 */
public class LoginPage {
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(LoginPage.class.getName());

    // Declared the private driver of Login page
    @SuppressWarnings("unused")
    private final WebDriver driver;
    private final DriverActions driverActions;
    private final DriverWaits driverWaits;

    @FindBy(id = "email")
    private WebElement usernameField;

    @FindBy(id = "pass")
    private WebElement passwordField;

    @FindBy(name = "login")
    private WebElement submitButton;

    /**
     * Constructor for a LoginPage class.
     *
     * @param driver - An {@link WebDriver} type object.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.driverActions = Pages.getDriverActionsObject();
        this.driverWaits = Pages.getDriverWaitsObject();
    }

    /**
     * Implementing login functionality.
     * Verify successfully login into the application.
     *
     * @param loginCredentials - An object of {@link LoginCredentialsData} class containing login credentials.
     * @return true if successfully logged in into application or false otherwise.
     */
    public boolean login(LoginCredentialsData loginCredentials) {
        try {
            if(loginCredentials.getTestValidateStatus() == null || loginCredentials.getTestValidateStatus().isEmpty()) {
                System.out.println("Proceed with conventional work.");
            }
            else if (loginCredentials.getTestValidateStatus().equalsIgnoreCase("Skip")) {
                throw new Exception("Login into the application is skipped");
            }
            else if(loginCredentials.getTestValidateStatus().equalsIgnoreCase("pass")) {
                Reporter.log("Deliberately pass the test case due to \"" + loginCredentials.getTestValidateFailComments() + "\"");
                return true;
            }
            else {

            }

            driverWaits.explicitWait(5); // Wait for a dummy element

            // Click and provide username
            Assert.assertTrue(driverActions.clickOnWebElementWithActionsClass(usernameField), "Unable to click on user name field.");
            Assert.assertTrue(driverActions.typeText(usernameField, loginCredentials.getUsername()), "Unable to type text on user name field.");

            // Click and provide password
            Assert.assertTrue(driverActions.clickOnWebElementWithActionsClass(passwordField), "Unable to click on password field.");
            Assert.assertTrue(driverActions.typeText(passwordField, loginCredentials.getPassword()), "Unable to type text on password field.");

            Assert.assertTrue(driverActions.clickOnWebElementWithActionsClass(submitButton), "Unable to click on submit button.");



            driverWaits.explicitWait(10); // Wait for a dummy element

            return true;

        } catch(Exception ex) {
            ex.printStackTrace();

            if(loginCredentials.getTestValidateFailComments().isEmpty()) {
                Reporter.log(ex.getMessage() + ".");
            }
            else {
                Reporter.log(ex.getMessage() + " for '" + loginCredentials.getTestValidateFailComments() + "'.");
            }

            return false;
        }
    }
}
