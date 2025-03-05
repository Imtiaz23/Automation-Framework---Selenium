package framework.testcases;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.ITestContext;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.applicationsettings.ApplicationSettings;
import framework.commonutils.Browser;

public class TestBase {
    @SuppressWarnings("unused")

    private static Logger log = Logger.getLogger(TestBase.class.getName());
    private StopWatch totalTestTime = new StopWatch();
    public static int pass = 0;
    public static int fail = 0;
    public static int total = 0;
    public static boolean needDictionary = true;


    @BeforeSuite(alwaysRun = true)
    public void setUp(ITestContext iTest) {
        totalTestTime.start();

		/*Following suites are using silverdatarp tenant.
		As per the discussion with Madhuri, from May 2020 new URL (https://secure.eng.pointillist.com/) will be used for silverdatarp tenant.
		For other tenants the old URL (https://stress-secure-pointillist.altidev.net/) will be used. This is applicable for stress environment only.*/
        String[] newStressSuites = new String[]{
                "RolesPemissionSanitySuite",
                "RolesPermissionsMatrixValidation",
                "PoliciesPermissionsMatrixValidation",
                "EngagementsSanityTestSuite"};

        String suiteName = iTest.getCurrentXmlTest().getSuite().getName();

        String os = System.getProperty("executionOS");
        String browser = System.getProperty("executionBrowser");
        String browserState = System.getProperty("executionBrowserState");
        String environment = System.getProperty("executionEnvironment");

        if (environment == null || environment.isEmpty() || environment.equalsIgnoreCase("Facebook")) {
            int totalNewSuites = newStressSuites.length;

            for (int i = 0; i < totalNewSuites; i++) {
                if (suiteName.equalsIgnoreCase(newStressSuites[i])) {
                    environment = "StressSilverdataRP";
                    break;
                }
            }
        }

        ApplicationSettings.setUp(os, browser, browserState, environment);

        Browser.initialize(iTest.getCurrentXmlTest().getSuite().getName());

        // Logging is initialized for a particular test suite
        if (iTest.getCurrentXmlTest().getSuite().getName().equalsIgnoreCase("ApplicationLoginAndLogoutValidation")) {
            DOMConfigurator.configure("log4j.xml");
        } else {

        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        Browser.closeWebDriver();

        System.out.println("End Test " + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format((Calendar.getInstance()).getTime()));
        totalTestTime.stop();
        long testTime = totalTestTime.getTime();
        System.out.printf("Total time taken:\t" + testTime + " (" + "%.3f" + " mins)\n", ((double) testTime / 60000));
    }
}
