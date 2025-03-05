package framework.applicationsettings;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public final class ApplicationSettings {
    @SuppressWarnings("unused")

    private static Logger log = Logger.getLogger(ApplicationSettings.class.getName());

    private static String fileSuffix;
    private static String fileExt;
    private static String browserName;
    private static boolean browserHeadlessState;
    private static String environmentType;
    private static String url;
    private static String inputFilePath;
    private static String referenceListEngagementFilePath;
    private static String timeStamp;
    private static String dateTimeStamp;
    private static boolean locatorUsingFlag;

    // Environment Details for Facebook
    private static final String stressUrl = "https://www.facebook.com/";
    private static final String stressInputFilePath = ".//Test_Data//TestData_fb.xls";

    // Image Upload directory
    private static final String imageFolderPath = ".//Test_Data//IMAGES_FOLDER//";

    // Unique String for User Agent
    private static final String uniqueStringUserAgent = "UI_Automation";

    private ApplicationSettings () { // private constructor
    }

    public static void setUp(String os, String browser, String browserState, String environment) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if(os != null && os.equalsIgnoreCase("Win")) {
            fileExt = ".exe";
            fileSuffix = "";
        }
        else if(os != null && os.equalsIgnoreCase("Linux")) {
            fileExt = "";
            fileSuffix = "Linux";
        }
        else {
            fileExt = "";
            fileSuffix = "";
        }

        if(browser == null)
            browserName = "Chrome";
        else
            browserName = browser;

        if(browserState != null && browserState.equalsIgnoreCase("Headless"))
            browserHeadlessState = true;
        else
            browserHeadlessState = false;

        if(environment == null || environment.equals("") || environment.equalsIgnoreCase("Facebook")) {
            url = stressUrl;
            inputFilePath = stressInputFilePath;
            environmentType = environment;
            timeStamp = dateTimeFormat.format(timestamp);
        }
        else if(environment.equalsIgnoreCase("Dev")) {

        }
    }

    public static String getBrowserName() {
        return browserName;
    }

    public static boolean getBrowserHeadlessState() {
        return browserHeadlessState;
    }

    public static String getUniqueStringForUserAgent() {
        return uniqueStringUserAgent;
    }

    public static String getUrl() {
        return url;
    }

    public static String getChromeDriverName() {
        return "webdriver.chrome.driver";
    }

    public static String getChromeDriverPath() {
        return ".//lib//chromedriver" + fileSuffix + fileExt;
    }

    public static String getFirefoxDriverName() {
        return "webdriver.gecko.driver";
    }

    public static String getFirefoxDriverPath() {
        return ".//lib//geckodriver" + fileSuffix + fileExt;
    }

    public static String getInputFilePath() {
        return inputFilePath;
    }

    public static String getTestOutputDirectory() {
        return ".//target//surefire-reports/";
    }

    public static String getDownloadFilePath() {
        return System.getProperty("user.dir") + "\\target\\surefire-reports\\Downloads";
    }

    public static String getLogin() {
        return "Login";
    }

    public static String getLoginCredentials() {
        return "[Generic_automation]_fblogin";
    }

    // Get Drag nad drop js file
    public static String getDragAndDropJsFile() {
        return "src//com//ca//nextgen//buf//ui//commonutils//DragAndDrop.js";
    }

}
