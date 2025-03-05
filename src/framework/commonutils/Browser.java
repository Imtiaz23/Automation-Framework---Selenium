package framework.commonutils;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.applicationsettings.ApplicationSettings;

public final class Browser {
    @SuppressWarnings("unused")

    private static Logger log = Logger.getLogger(Browser.class.getName());
    private static WebDriver webDriver;
    private static Proxy seleniumProxy;

    private Browser () { // private constructor
        webDriver = null;
        seleniumProxy = null;
    }

    public static WebDriver getWebDriver() {
        return webDriver;
    }


    public static WebDriverWait getWait()
    {
        return getWait(30);
    }

    public static WebDriverWait getWait(int wait)
    {
        return new WebDriverWait(webDriver, wait);
    }

    public static void initialize(String suiteName) {
        try {
            String browserName = ApplicationSettings.getBrowserName();
            boolean browserHeadlessState = ApplicationSettings.getBrowserHeadlessState();
            String url = ApplicationSettings.getUrl();
            String unigueStringUserAgent = ApplicationSettings.getUniqueStringForUserAgent();
            System.out.println(url);

             if (browserName.equalsIgnoreCase("Chrome")) {
                String chDriverName = ApplicationSettings.getChromeDriverName();
                String chDriverPath = ApplicationSettings.getChromeDriverPath();
                String downloadFilepath = ApplicationSettings.getDownloadFilePath();
                System.out.println(chDriverName);
                System.out.println(chDriverPath);

                System.setProperty(chDriverName, chDriverPath);
                System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, chDriverPath);

                Map<String, Object> preferences = new Hashtable<String, Object>();
                preferences.put("profile.default_content_settings.popups", 0);
                preferences.put("download.default_directory", downloadFilepath);

                // disable flash and the PDF viewer
                preferences.put("plugins.plugins_disabled", new String[]{
                        "Adobe Flash Player", "Chrome PDF Viewer"});

                LoggingPreferences loggingprefs = new LoggingPreferences();
                loggingprefs.enable(LogType.PERFORMANCE, Level.ALL);

                ChromeOptions options = new ChromeOptions();
                options.setHeadless(browserHeadlessState);
                options.setExperimentalOption("prefs", preferences);
                options.setProxy(seleniumProxy); // Set proxy server into chrome webdriver
                options.addArguments("--test-type");
                options.addArguments("--ignore-certificate-errors");
                options.addArguments("--disable-notifications");
                options.addArguments("chrome.switches");
                options.addArguments("disable-infobars");
                webDriver = new ChromeDriver();
                String userAgent = (String) ((JavascriptExecutor) webDriver).executeScript("return navigator.userAgent;");

            }
            else if (browserName.equalsIgnoreCase("Firefox")) {
                String firefoxDriverName = ApplicationSettings.getFirefoxDriverName();
                String firefoxDriverPath = ApplicationSettings.getFirefoxDriverPath();
                System.out.println(firefoxDriverName);
                System.out.println(firefoxDriverPath);

                System.setProperty(firefoxDriverName, firefoxDriverPath);

                FirefoxOptions options = new FirefoxOptions();
                options.setProxy(seleniumProxy); //Set proxy server into firefox webdriver
                options.setCapability("acceptSslCerts", true);
                options.addArguments("--test-type");
                options.addArguments("ignore-certificate-errors");
                options.addArguments("--disable-notifications");
                options.addArguments("firefox.switches", "--disable-extensions");
                options.addArguments("disable-infobars");
                webDriver = new FirefoxDriver();
                String userAgent = (String) ((JavascriptExecutor) webDriver).executeScript("return navigator.userAgent;");
                webDriver.close();
                options.addPreference("general.useragent.override", userAgent + " " + unigueStringUserAgent);

                webDriver = new FirefoxDriver(options);
            }

            webDriver.manage().deleteAllCookies();

            webDriver.manage().window().maximize();

        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
            System.out.println("error" + ex);
        }
    }

    public static void closeWebDriver() {
        try
        {
            webDriver.close();
        }
        catch (WebDriverException ex)
        {
            ex.printStackTrace();
        }

        try
        {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
        catch (WebDriverException ex)
        {
            ex.printStackTrace();
        }
    }
}
