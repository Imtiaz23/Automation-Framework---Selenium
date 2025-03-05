package framework.commonutils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import framework.applicationsettings.ApplicationSettings;
import framework.pages.Pages;


public class DriverActions {
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(DriverActions.class.getName());

    private final WebDriver driver;
    private final DriverWaits driverWaits;

    @FindBy(id = "dummyWait")
    private WebElement dummyWaitElement;

    public DriverActions(WebDriver driver) {
        this.driver = driver;
        this.driverWaits = Pages.getDriverWaitsObject();
    }

    // Writing helper method to click on web element
    public boolean clickOnWebElementWithActionsClass(WebElement element) throws Exception {
        try {
            Assert.assertTrue(scrollToWebElementWithJavaScript(element), "Unable to scroll to element.");

            (new Actions(driver)).moveToElement(Browser.getWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(element))).click().build().perform();

            // Accept any browser alert is popped up on the screen
            if (acceptBrowserAlert()) {
                System.out.println("Alert is present.");
            } else {
                System.out.println("Alert is absent.");
            }
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing clickOnWebElementWithActionsClassUsingOffset functionality.
     * Following is a helper method designed click on an webelement on an offset
     *
     * @param element - An object of {@link WebElement} class containing the WebElement object that needs to be scrolled to.
     * @param xOffset - An object of {@link int} containing offset value along X axis
     * @param -       An object of {@link int} containing offset value along Y axis
     * @return true if clicking on WebElement is successful or false otherwise.
     */
    public boolean clickOnWebElementWithActionsClassUsingOffset(WebElement element, int xOffset, int yOffset) throws Exception {
        try {
            Assert.assertTrue(scrollToWebElementWithJavaScript(element), "Unable to scroll to element.");

            (new Actions(driver)).moveToElement(Browser.getWait()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.elementToBeClickable(element)), xOffset, yOffset).click().build().perform();

            // Accept any browser alert is popped up on the screen
            if (acceptBrowserAlert()) {
                System.out.println("Alert is present.");
            } else {
                System.out.println("Alert is absent.");
            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing clickOnWebElementUsingJavaScript functionality.
     * Following is a helper method designed click on an webelement using JavaScript executor.
     *
     * @param element - An object of {@link WebElement} class containing the WebElement object that needs to be clicked.
     * @return true if clicking on WebElement is successful or false otherwise.
     */
    public boolean clickOnWebElementUsingJavaScript(WebElement element) {
        try {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("var elem=arguments[0]; setTimeout(function() {elem.click();}, 60)", element);

            // Accept any browser alert is popped up on the screen
            if (acceptBrowserAlert()) {
                System.out.println("Alert is present.");
            } else {
                System.out.println("Alert is absent.");
            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Writing helper method to scroll to web element
    public boolean scrollToWebElementWithJavaScript(WebElement element) throws Exception {
        try {
            Browser.getWait(20).until(ExpectedConditions.visibilityOf(element));

            return true;

        } catch (TimeoutException te) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing scrollToWebElementWithJavaScriptWithoutCheckingVisibility functionality.
     * Following is a helper method designed to scroll to a WebElement without checking visibility prior to scrolling
     *
     * @param element - An object of {@link WebElement} class containing
     *                the WebElement object that needs to be scrolled to.
     * @return true if scrolling to WebElement is successful or false otherwise.
     */
    public boolean scrollToWebElementWithJavaScriptWithoutCheckingVisibility(WebElement element) throws Exception {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Writing helper method to do mouse hover on web element
    public boolean mouseHoverOnWebElement(WebElement element, String xOffset, String yOffset) {
        return mouseHoverOnWebElement(element, xOffset, yOffset, false);
    }

    // Writing helper method to do mouse hover on web element
    // based on visibility check ignoring for the web element
    public boolean mouseHoverOnWebElement(WebElement element, String xOffset, String yOffset, boolean ignoreVisibilityCheckingFlag) {
        try {
            if (ignoreVisibilityCheckingFlag) {
                Browser.getWait().ignoring(StaleElementReferenceException.class);
                (new Actions(driver)).moveToElement(element).build().perform();
            } else {
                if ((xOffset == null || xOffset.equals("")) && (yOffset == null || yOffset.equals(""))) {
                    (new Actions(driver)).moveToElement(Browser.getWait()
                            .ignoring(StaleElementReferenceException.class)
                            .until(ExpectedConditions.visibilityOf(element))).build().perform();
                } else {
                    Integer elementHeight = element.getSize().height;
                    if (Integer.valueOf(yOffset) > elementHeight) {
                        yOffset = String.valueOf(elementHeight);
                    }
                    (new Actions(driver)).moveToElement(Browser.getWait()
                            .ignoring(StaleElementReferenceException.class)
                            .until(ExpectedConditions.visibilityOf(element)), Integer.valueOf(xOffset), Integer.valueOf(yOffset)).build().perform();
                }
            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Helper method to select dropdown item
    public boolean selectDropdownItem(WebElement element, String item) {
        try {

            Browser.getWait().until(ExpectedConditions.visibilityOf(element));

            Select select = new Select(element);
            select.selectByVisibleText(item);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Helper method to select unit dropdown item
    public boolean selectItemFromUnitDropdown(WebElement element, String locator, String item) {
        try {
            Browser.getWait().until(ExpectedConditions.visibilityOf(element));
            Assert.assertTrue(clickOnWebElementWithActionsClass(element), "Unable to open dropdown");

            String newULLocator = locator + " ul li";

            List<WebElement> dropdownItems = driver.findElements(By.cssSelector(newULLocator));

            int temp = 0;

            for (WebElement dropdownSingularItem : dropdownItems) {
                if (dropdownSingularItem.getText().contains(item)) {
                    Assert.assertTrue(clickOnWebElementWithActionsClass(dropdownSingularItem), "Unable to select dropdown item.");
                    temp = 1;
                    break;
                }
            }

            if (temp == 0) {
                return false;
            } else
                return true;
        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Writing helper method to drag a element and drop it to another place of the page
    public boolean dragAndDropWebElementWithActionsClass(WebElement sourceElement, WebElement destinationElement, Integer xOffset, Integer yOffset) throws Exception {
        try {
            driverWaits.explicitWait(5);// Wait for a dummy element
            Browser.getWait().until(ExpectedConditions.elementToBeClickable(sourceElement));
            Browser.getWait().until(ExpectedConditions.visibilityOf(destinationElement));

            (new Actions(driver)).clickAndHold(Browser.getWait().until(ExpectedConditions.elementToBeClickable(sourceElement)))
                    .moveToElement(destinationElement, xOffset, yOffset)
                    .moveToElement(destinationElement, xOffset, yOffset)
                    .release().build().perform();

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Writing helper method to drag a element and drop it to another place of the page using only source and destination elements
    public boolean dragAndDropWebElementWithActionsClass(WebElement sourceElement, WebElement destinationElement) {
        try {
            driverWaits.explicitWait(5);// Wait for a dummy element
            Browser.getWait().until(ExpectedConditions.elementToBeClickable(sourceElement));
            Browser.getWait().until(ExpectedConditions.visibilityOf(destinationElement));

            (new Actions(driver)).dragAndDrop(sourceElement, destinationElement).build().perform();

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Writing helper method to drag a element and drop it to another place of the page by java script
    public boolean dragAndDropWebElementWithJavaScript(WebElement elementToDrag, WebElement targetCanvas) {
        try {
            String dragAndDropJS = new String(Files.readAllBytes(Paths.get(ApplicationSettings.getDragAndDropJsFile())), "UTF-8");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(dragAndDropJS, elementToDrag, targetCanvas);

            // Wait for a dummy element
            try {
                Browser.getWait(5).until(ExpectedConditions.visibilityOf(dummyWaitElement));
            } catch (Exception ex) {

            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing typeText functionality
     * Following method takes text as input for an element.
     *
     * @param element - Element that will contain the input text
     * @param text    - Receives the input text
     * @return true if text is provided properly or false otherwise
     */
    public boolean typeText(WebElement element, String text) {
        try {
            // Scroll to the desired element
            Assert.assertTrue(scrollToWebElementWithJavaScript(element), "Unable to scroll to element.");

            Browser.getWait().until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            driverWaits.explicitWait(5);
            try {
                if (!element.getAttribute("class").isEmpty() && element.getAttribute("class").contains("ng")) {
                    if (!element.getAttribute("class").contains("text-area")) {
                        if (!element.getAttribute("class").contains("ng-not-empty")) {
                            Actions actions = new Actions(driver);
                            actions.moveToElement(element);
                            actions.sendKeys(text);
                            actions.build().perform();
                        }
                    }
                }
            } catch (Exception es) {
                es.printStackTrace();
            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    public boolean typeTextAction(WebElement element, String text) {
        try {
            element.clear();
            Actions actions = new Actions(driver);
            actions.moveToElement(element);
            actions.sendKeys(text);
            actions.build().perform();

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing dragItem functionality
     * Following method drags an item according to the provided offset value to different axis.
     *
     * @param element              - A {@link WebElement} object to drag.
     * @param positionTowardsXAxis - {@link Integer} value towards X axis.
     * @param positionTowardsYAxis - {@link Integer} value towards Y axis.
     * @return true after dragging the element to desired position successfully or false otherwise.
     */
    public boolean dragItem(WebElement element, int positionTowardsXAxis, int positionTowardsYAxis) {
        try {
            Browser.getWait().until(ExpectedConditions.visibilityOf(element));

            // Drag calculated attributes editor
            Actions builder = new Actions(driver);
            Action dragAndDrop = builder.dragAndDropBy(element, positionTowardsXAxis, positionTowardsYAxis)
                    .build();

            dragAndDrop.perform();

            // Wait for a dummy element
            try {
                Browser.getWait(3).until(ExpectedConditions.visibilityOf(dummyWaitElement));
            } catch (Exception ex) {

            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Writing helper method to accept browser popped up alert
    public boolean acceptBrowserAlert() {
        try {
            Browser.getWait(1).until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            driver.switchTo().defaultContent();

            return true;

        } catch (TimeoutException te) {

            return false;
        } catch (NoAlertPresentException nape) {

            return false;
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }

    // Writing helper method to navigate to a newly opened tab
    public boolean navigateToNewTab() {
        try {
            // Wait for a dummy element
            try {
                Browser.getWait(15).until(ExpectedConditions.visibilityOf(dummyWaitElement));
            } catch (Exception ex) {

            }

            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            if (tabs.size() > 1) {
                driver.close();
                driver.switchTo().window(tabs.get(1));
            }
            System.out.println(driver.getCurrentUrl().toString());

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Writing helper method to close a newly opened tab
    public boolean closeNewTab(String returnHandle) {
        try {
            // Wait for a dummy element
            try {
                Browser.getWait(15).until(ExpectedConditions.visibilityOf(dummyWaitElement));
            } catch (Exception ex) {

            }

            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            if (tabs.size() > 1) {
                driver.switchTo().window(tabs.get(1));
                driver.close();
            }
            driver.switchTo().window(returnHandle);
            System.out.println(driver.getCurrentUrl().toString());

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    // Check if a new tab is opened and verify url
    public boolean checkUrlWithNewTab(String newTabUrl) {
        try {
            // Wait for a dummy element
            try {
                Browser.getWait(15).until(ExpectedConditions.visibilityOf(dummyWaitElement));
            } catch (Exception ex) {

            }

            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            if (tabs.size() > 1) {
                driver.switchTo().window(tabs.get(1));
            }

            if (!driver.getCurrentUrl().contains(newTabUrl)) {
                Assert.fail("Unable to verify new tab and url.");
            }

            if (tabs.size() > 1) {
                driver.close();
                driver.switchTo().window(tabs.get(0));
            } else {

            }


            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing switchToBrowserTab functionality
     * Following method switches to different tab by tab title
     *
     * @param tabTitle - Receives title of a tab
     * @return true if switching of tab is properly performed or false otherwise
     */
    public boolean switchToBrowserTab(String tabTitle) {
        try {
            // Wait for a dummy element
            try {
                Browser.getWait(5).until(ExpectedConditions.visibilityOf(dummyWaitElement));
            } catch (Exception ex) {

            }

            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            for (int index = 0; index < tabs.size(); index++) {
                driver.switchTo().window(tabs.get(index));
                System.out.println(driver.getTitle());
                if (driver.getTitle().contains(tabTitle)) {
                    break;
                }
            }
            System.out.println(driver.getCurrentUrl().toString());

            // Wait for a dummy element
            try {
                Browser.getWait(5).until(ExpectedConditions.visibilityOf(dummyWaitElement));
            } catch (Exception ex) {

            }

            // Wait until all the loading spinners become invisible
            Assert.assertTrue(driverWaits.waitUntilSpinnersDisappear(120), "Spinners were not invisible");

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * The method below returns true if an element is not displayed
     *
     * @param webElement
     * @param driver
     */
    public boolean waitUntilInvisibilityOfElement(final WebElement webElement, WebDriver driver) {
        boolean displayFlag = Browser.getWait().until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver arg0) {
                try {
                    webElement.isDisplayed();
                    return false;
                } catch (NoSuchElementException e) {
                    return true;
                } catch (StaleElementReferenceException f) {
                    return true;
                }
            }
        });

        return displayFlag;
    }

    /**
     * Implementing refreshPage functionality.
     * Following method refreshes the current page and wait for twenty seconds to load the page
     *
     * @return true when the page is properly loaded or false otherwise
     */
    public boolean refreshPage() {
        try {
            driver.navigate().refresh();

            if (acceptBrowserAlert()) {
                System.out.println("Alert is present.");

                // Wait for a dummy element
                try {
                    Browser.getWait(25).until(ExpectedConditions.visibilityOf(dummyWaitElement));
                } catch (Exception ex) {

                }
            } else {
                System.out.println("Alert is absent.");
            }

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing selectWebElementWithControlPressed functionality
     * Following method selects multiple elements using control button pressed
     *
     * @param elementList - List of items that have to be selected
     * @return true if the items are properly selected or false otherwise
     * @throws Exception
     */
    public boolean selectWebElementWithControlPressed(List<WebElement> elementList) throws Exception {
        try {
            Actions clickAction = new Actions(driver);

            for (WebElement webElement : elementList) {
                Assert.assertTrue(scrollToWebElementWithJavaScript(webElement), "Unable to scroll to element.");

                clickAction.keyDown(Keys.CONTROL).moveToElement(Browser.getWait()
                        .ignoring(StaleElementReferenceException.class)
                        .until(ExpectedConditions.elementToBeClickable(webElement))).click()
                        .keyUp(Keys.CONTROL);
            }

            clickAction.build().perform();

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing pressTab functionality
     * Following method press tab button from an element
     *
     * @param element - Receives WebElement as parameter
     * @return true after pressing the tab button or false otherwise
     * @throws Exception
     */
    public boolean pressTab(WebElement element) throws Exception {
        try {
            Browser.getWait(5).until(ExpectedConditions.visibilityOf(element));

            element.sendKeys(Keys.TAB);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing navigateToUrl functionality
     * Following method serves the operation to navigate to an url.
     *
     * @param url - {@link String} that takes the desired url
     * @return true if the desired url is loaded on the screen or false otherwise.
     */
    public boolean navigateToUrl(String url) {
        try {
            driver.get(url);

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing uploadImage functionality
     * Following method uploads the image.
     *
     * @param element   - Receives WebElement as parameter
     * @param imageName - Takes the name of the image
     * @return true if image can be uploaded or false otherwise.
     */
    /*public boolean uploadImage(WebElement element, String imageName) {
        try {
            File file = new File(ApplicationSettings.getImagePath() + imageName); // Fetching the file path
            element.sendKeys(file.getAbsolutePath()); // Uploading the image

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }*/


    /**
     * Implementing verifyTextVisibility functionality.
     * Verifies the text visibility in provided elements list.
     *
     * @param referenceName - An object of {@link String} containing the reference text to match with the webElement text.
     * @param list          - A list of {@link WebElement} contains the list of webElement to to match the text with the referenceName.
     * @return true if webElement text matches the referenceName or false otherwise.
     */
    public boolean verifyTextVisibility(String referenceName, List<WebElement> list) {
        try {
            int length = list.size();
            Boolean findStatus = false;

            for (int i = 0; i < length; i++) {
                if (list.get(i).getText().equalsIgnoreCase(referenceName)) {
                    findStatus = true;
                    break;
                }
            }

            return findStatus;

        } catch (NoSuchElementException ex) {
            System.out.println("Element was not present.");

            return false;
        }
    }

    /**
     * Implementing verifyElementInvisibilityByCss functionality.
     * Verifies the element invisibility in WebPage.
     *
     * @param referenceCssSelector - An object of {@link String} containing the reference css selector of the webElement.
     * @return true if webElement is invisible or false otherwise.
     */
    public boolean verifyElementInvisibilityByCss(String referenceCssSelector) {
        try {
            boolean invisibleStatus = true;
            try {
                Browser.getWait(5).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(referenceCssSelector)));
                invisibleStatus = false;
            } catch (Exception ex) {
                System.out.println("Reference element is expectedly invisible.");
            }

            return invisibleStatus;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing verifyElementVisibilityByCss functionality.
     * Verifies the element visibility in WebPage.
     *
     * @param referenceCssSelector - An object of {@link String} containing the reference css selector of the webElement.
     * @return true if webElement is visible or false otherwise.
     */
    public boolean verifyElementVisibilityByCss(String referenceCssSelector) {
        try {
            boolean visibleStatus = false;
            try {
                Browser.getWait(5).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(referenceCssSelector)));
                visibleStatus = true;
            } catch (Exception ex) {
                System.out.println("Reference element is could not be found.");
            }

            return visibleStatus;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing verifyElementVisibility functionality.
     * Verifies the element visibility in WebPage.
     *
     * @param - An object of {@link String} containing the reference css selector of the webElement.
     * @return true if webElement is visible or false otherwise.
     */
    public boolean verifyElementVisibility(WebElement referenceElement) {
        try {
            boolean visibleStatus = false;
            try {
                scrollToWebElementWithJavaScript(referenceElement);
                Browser.getWait(5).until(ExpectedConditions.visibilityOf(referenceElement));
                visibleStatus = true;
            } catch (Exception ex) {
                System.out.println("Reference element could not be found.");
            }

            return visibleStatus;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing verifyElementIsClickable functionality.
     * Verifies the element clickable in WebPage.
     *
     * @param - An object of {@link String} containing the reference css selector of the webElement.
     * @return true if webElement is visible or false otherwise.
     */
    public boolean verifyElementIsClickable(WebElement referenceElement) {
        try {
            boolean clickableStatus = false;
            try {
                scrollToWebElementWithJavaScript(referenceElement);
                Browser.getWait(5).until(ExpectedConditions.elementToBeClickable(referenceElement));
                clickableStatus = true;
            } catch (Exception ex) {
                System.out.println("Reference element could not be found.");
            }

            return clickableStatus;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing verifyElementInvisibility functionality.
     * Verifies the element invisibility in WebPage.
     *
     * @param - An object of {@link String} containing the reference css selector of the webElement.
     * @return true if webElement is invisible or false otherwise.
     */
    public boolean verifyElementInvisibility(WebElement referenceElement) {
        try {
            boolean invisibleStatus = true;
            try {
                scrollToWebElementWithJavaScript(referenceElement);
                Browser.getWait(5).until(ExpectedConditions.visibilityOf(referenceElement));
                invisibleStatus = false;
            } catch (Exception ex) {
                System.out.println("Reference element was found invisible as expected.");
            }

            return invisibleStatus;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing verifyTextToBePresent functionality.
     * Verifies the element contains a specified text within specified seconds.
     *
     * @param referenceElement - An object of {@link WebElement} contains the reference WebElement.
     * @param text             - An object of {@link String} contains the reference text.
     * @param seconds          - An object of {@link int} contains the explicit waiting seconds until the text is being visible.
     * @return true if text becomes present within specified seconds or false otherwise
     */
    public boolean verifyTextToBePresent(WebElement referenceElement, String text, int seconds) {
        try {
            boolean textVisibilityStatus = false;
            try {
                scrollToWebElementWithJavaScript(referenceElement);
                Browser.getWait(5).until(ExpectedConditions.visibilityOf(referenceElement));
                Browser.getWait(seconds).until(ExpectedConditions.textToBePresentInElement(referenceElement, text));
                textVisibilityStatus = true;

            } catch (Exception ex) {
                System.out.println("Text was not present in the webelement within the specified " + seconds + " seconds.");
            }

            return textVisibilityStatus;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    /**
     * Implementing verifyTextToBePresent functionality.
     * Verifies the element contains a specified text within default 30 seconds.
     *
     * @param referenceElement - An object of {@link WebElement} contains the reference WebElement.
     * @param text             - An object of {@link String} contains the reference text.
     * @return true if text becomes present within default 30 seconds seconds or false otherwise
     */
    public boolean verifyTextToBePresent(WebElement referenceElement, String text) {
        try {
            boolean textVisibilityStatus = false;
            try {
                scrollToWebElementWithJavaScript(referenceElement);
                Browser.getWait(5).until(ExpectedConditions.visibilityOf(referenceElement));
                Browser.getWait(30).until(ExpectedConditions.textToBePresentInElement(referenceElement, text));
                textVisibilityStatus = true;

            } catch (Exception ex) {
                System.out.println("Text was not present in the webelement within 30 seconds.");
            }

            return textVisibilityStatus;

        } catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }

    public boolean ForceScrollToWebElementWithJavaScript(WebElement element) throws Exception {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

            return true;

        }
        catch (Exception ex) {
            ex.printStackTrace();

            return false;
        }
    }
}
