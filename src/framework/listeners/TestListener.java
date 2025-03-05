package framework.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import framework.applicationsettings.ApplicationSettings;
import framework.commonutils.Browser;

public class TestListener implements ITestListener {
    private static Logger log = Logger.getLogger(TestListener.class);
    private boolean succeededTestStatus = true;
    private boolean loginStatus = false;
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");


    // This belongs to ITestListener and will execute before starting of Test set/batch
    public void onStart(ITestContext arg0) {
        log.info(arg0.getName() + " test batch execution is Started");
        System.out.println("Status of the test flag: " + succeededTestStatus);
        System.out.println("About to begin executing test: " + arg0.getName());
    }

    // This belongs to ITestListener and will execute, once the Test set/batch is finished
    public void onFinish(ITestContext arg0) {
        log.info(arg0.getName() + " test batch execution is Finished");
        System.out.println("Final status of the test flag: " + succeededTestStatus);
        System.out.println("Completed executing test: " + arg0.getName());
    }

    // This belongs to ITestListener and will execute only when the test is pass
    public void onTestSuccess(ITestResult arg0) {
        log.info(arg0.getMethod().getMethodName() + " test case is PASSED");
        if(succeededTestStatus && arg0.getMethod().getMethodName() == "verifyLogin") {
            loginStatus = true;
        }

        // Calling browser log without writing into console output
        try {
            Browser.getWebDriver().manage().logs().get(LogType.BROWSER);
        } catch (Exception e) {

        }
    }

    // This belongs to ITestListener and will execute only on the event of fail test
    public void onTestFailure(ITestResult arg0) {
        log.error(arg0.getMethod().getMethodName() + " test case is FAILED", arg0.getThrowable());
        skipLaterTestCases(arg0);

        // Capturing browser screenshot
        try {
            String screenshot = "Screenshots/screenshot_" + dateTimeFormat.format((Calendar.getInstance()).getTime()) + ".png";
            takeSnapShot(Browser.getWebDriver(), screenshot);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Log display resolution in both Report and console output
        Reporter.log("Execution Server Display Resolution: " + Browser.getWebDriver().manage().window().getSize().toString(), true);

        // Calling browser log and writing into console output
        try {
            LogEntries browserLogEntries = Browser.getWebDriver().manage().logs().get(LogType.BROWSER);
            printBrowserConsoleErrors(browserLogEntries);
        } catch (Exception e) {

        }
    }

    // This belongs to ITestListener and will execute before the main test start (@Test)
    public void onTestStart(ITestResult arg0) {
        if(!succeededTestStatus) {
            if(loginStatus == true && arg0.getMethod().getMethodName() == "verifyLogout") {
                System.out.println(loginStatus);
            }
            else
                throw new SkipException("Skip the test case");
        }

        else
            System.out.println("Starting execution of the test case: " + arg0.getName());
    }

    // This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped
    public void onTestSkipped(ITestResult arg0) {
        log.info(arg0.getMethod().getMethodName() + " test case is SKIPPED");
        skipLaterTestCases(arg0);

        // Calling browser log without writing into console output
        try {
            Browser.getWebDriver().manage().logs().get(LogType.BROWSER);
        } catch (Exception e) {

        }
    }

    // This belongs to ITestListener and will execute only if any of the main test(@Test) fails within a success percentage
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
    }

    // This method sets the 'succeededTestStatus' flag to true/false based on the status of test case
    private void skipLaterTestCases(ITestResult result) {
        if(result.getMethod().getMethodName() == "verifyLogin" || result.getMethod().getMethodName().contains("verifyOpenExistingStory")) {
            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    succeededTestStatus = false;
                    break;

                case ITestResult.SKIP:
                    succeededTestStatus = false;
                    break;

                default:
                    succeededTestStatus = true;
                    loginStatus = true;
            }
        }
        else {
        }
    }

    private static void takeSnapShot(WebDriver webDriver,String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webDriver);

        //Call getScreenshotAs method to create image file

        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination

        File DestFile=new File(ApplicationSettings.getTestOutputDirectory() + fileWithPath);

        //Copy file at destination

        FileUtils.copyFile(SrcFile, DestFile);

        // Attach screenshot on report
        String path = "<a href=\"" + fileWithPath + "\"> <img width='100' height='100' src=\"" + fileWithPath + "\"> </a>";
        Reporter.setEscapeHtml(false);
        Reporter.log(path);

    }

    /**
     * Implementing printBrowserConsoleErrors functionality.
     * Log the browser js error into console output.
     *
     * @param logEntries - A {@link LogEntries} type object containing specific type log
     * i.e. Browser, Performance, Driver etc.
     * @throws Exception
     */
    private void printBrowserConsoleErrors(LogEntries logEntries) throws Exception {
        System.out.println("Started printing errors");
        for(LogEntry entry: logEntries) {
            System.out.println("Error Message in Console: " + entry.getMessage());
        }
        System.out.println("Finished printing errors");
    }
}

