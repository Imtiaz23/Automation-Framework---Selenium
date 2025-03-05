package framework.testcases;

import framework.pages.Pages;
import framework.applicationsettings.ApplicationSettings;
import framework.commonutils.Browser;
import framework.dataproviders.TestDataProviders;
import framework.pages.testdata.LoginCredentialsData;
import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginValidate extends TestBase {
    @SuppressWarnings("unused")

    private static Logger log = Logger.getLogger(LoginValidate.class.getName());

    @Test
    public void navigateToLoginPage() {
        try {
            WebDriver driver = Browser.getWebDriver();
            String applicationUrl = ApplicationSettings.getUrl();
            StopWatch pageLoad = new StopWatch();
            pageLoad.start();
            System.out.println("Begin Try navigateToLoginPage " + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format((Calendar.getInstance()).getTime()));

            driver.get(applicationUrl);

            pass++;

            System.out.println("End Try navigateToLoginPage " + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format((Calendar.getInstance()).getTime()));
            pageLoad.stop();
            long pageLoadTime = pageLoad.getTime();
            System.out.println("Time taken:\t" + pageLoadTime + "\n");
            Reporter.log(new Object(){}.getClass().getEnclosingMethod().getName() + "\t");
            Reporter.log(pageLoadTime + " MILLISECONDS");

        } catch (Exception e) {
            e.printStackTrace();
            fail++;
        }
    }

    @Test(dataProvider = "loginDP", dataProviderClass = TestDataProviders.class)
    public void verifyLogin(String userName, String passWord, String testValidateStatus, String testValidateFailComments) {
        try {
            StopWatch pageLoad = new StopWatch();
            pageLoad.start();
            System.out.println("Begin Try verifyLogin " + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format((Calendar.getInstance()).getTime()));

            LoginCredentialsData loginCredentials = new LoginCredentialsData();
            loginCredentials.setUsername(userName.trim());
            loginCredentials.setPassword(passWord.trim());
            loginCredentials.setTestValidateStatus(testValidateStatus.trim());
            loginCredentials.setTestValidateFailComments(testValidateFailComments.trim());

            Assert.assertTrue(Pages.getLoginPageObject().login(loginCredentials), "Unable to login into Pointillist Studio.");

            pass++;

            System.out.println("End Try verifyLogin " + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format((Calendar.getInstance()).getTime()));
            pageLoad.stop();
            long pageLoadTime = pageLoad.getTime();
            System.out.println("Time taken:\t" + pageLoadTime + "\n");
            Reporter.log(new Object(){}.getClass().getEnclosingMethod().getName() + "\t");
            Reporter.log(pageLoadTime + " MILLISECONDS");

        } catch (Exception e) {
            e.printStackTrace();
            fail++;
        }
    }
}
