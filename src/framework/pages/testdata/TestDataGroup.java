package framework.pages.testdata;

import framework.applicationsettings.ApplicationSettings;
import framework.commonutils.GetTestData;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public abstract class TestDataGroup {
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(TestDataGroup.class.getName());

    public static String checkForLoginTestDataGrouping(String group) {
        String tableName = null;

        if(group.equalsIgnoreCase("verifyLogin")) {
            tableName = ApplicationSettings.getLoginCredentials();
        }

        else {
        }

        return tableName;
    }
}
