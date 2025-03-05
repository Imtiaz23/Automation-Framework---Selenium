package framework.commonutils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * A helper class for driver waits.
 * Declared web elements on this page using page factory.
 *
 * @publicMethod
 * <p>
 * @use {@link #explicitWait(int)} method to wait a driver for a fixed period of time.
 * @use {@link #waitUntilSpinnersDisappear()} method to check all the loading spinners to be invisible with default timeout.
 * @use {@link #waitUntilSpinnersDisappear(int)} method to check all the loading spinners to be invisible with custom timeout.
 * </p>
 *
 * @author hasan
 *
 */
public class DriverWaits {
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(DriverWaits.class.getName());

    private final WebDriver driver;
    private By pageLoadingSpinner = By.id("caBeforelLoad");
    private By loadingBar = By.id("loading-bar");
    private By loadingBarSpinner = By.id("loading-bar-spinner");

    @FindBy(id = "dummyWait")
    private WebElement dummyWaitElement;

    /**
     * Constructor for a DriverWaits class.
     *
     * @param driver - An {@link WebDriver} type object.
     */
    public DriverWaits (WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Implementing explicitWait functionality.
     * Deliberately make a web driver wait for a certain period of time.
     *
     * @param waitTime - An int type object to provide waiting time.
     */
    public void explicitWait(int waitTime) {
        try {
            Browser.getWait(waitTime).until(ExpectedConditions.visibilityOf(dummyWaitElement));
        } catch (Exception ex) {
            System.out.println("Wait for " + waitTime + " seconds");
        }
    }

    /**
     * Implementing waitUntilSpinnersDisappear functionality.
     * Following methods checks the invisibility of all spinners with default timeout.
     *
     * @return true if all the spinners are invisible or false otherwise.
     */
    public boolean waitUntilSpinnersDisappear() {
        return waitUntilSpinnersDisappear(60); // By default 30 seconds has been inserted
    }

    /**
     * Implementing waitUntilSpinnersDisappear functionality.
     * Following methods check the invisibility of all spinners individually with custom timeout.
     *
     * @param waitTime - An int type object to provide waiting time.
     * @return true if all the spinners are invisible or false otherwise.
     */
    public boolean waitUntilSpinnersDisappear(int waitTime) {
        try {
            Browser.getWait(waitTime).until(ExpectedConditions.invisibilityOfElementLocated(pageLoadingSpinner));
            Browser.getWait(waitTime).until(ExpectedConditions.invisibilityOfElementLocated(loadingBar));
            Browser.getWait(waitTime).until(ExpectedConditions.invisibilityOfElementLocated(loadingBarSpinner));

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }
}
