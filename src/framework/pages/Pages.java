package framework.pages;

import org.openqa.selenium.support.PageFactory;

import framework.commonutils.Browser;
import framework.commonutils.DriverActions;
import framework.commonutils.DriverWaits;
import framework.pages.login.LoginPage;

public final class Pages {

    private static <T> T GetPage(Class<T> type) {
        return PageFactory.initElements(Browser.getWebDriver(), type);
    }

    public static DriverActions getDriverActionsObject() {
        return GetPage(DriverActions.class);
    }

    public static DriverWaits getDriverWaitsObject() {
        return GetPage(DriverWaits.class);
    }

    public static LoginPage getLoginPageObject() {
        return GetPage(LoginPage.class);
    }

}
