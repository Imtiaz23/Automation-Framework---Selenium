package framework.dataproviders;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import framework.applicationsettings.ApplicationSettings;
import framework.commonutils.GetTestData;
import framework.pages.testdata.TestDataGroup;

public class TestDataProviders {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(TestDataProviders.class.getName());
    private static String inputFile = ApplicationSettings.getInputFilePath();


    @DataProvider(name = "loginDP")
    public static Object[][] loginCredential(Method method) {
        String sheetName = ApplicationSettings.getLogin();
        String tableName = TestDataGroup.checkForLoginTestDataGrouping(method.getName());

        Object[][] testData = GetTestData.getdataFromDataSource(inputFile, sheetName, tableName);

        return testData;
    }
}
