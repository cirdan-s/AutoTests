package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lib.CoreTestCase;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import lib.Platform;
import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver){

        this.driver = driver;

    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeoutInSeconds) {

        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by));

    }

    public WebElement waitForElementPresent(String locator, String errorMessage) {

        return waitForElementPresent(locator, errorMessage, 5);

    }

    public void waitForElementAndClick(String locator, String errorMessage) {

        WebElement element = waitForElementPresent(locator, errorMessage, 5);


        for (int i = 1; i <= 5; i++) {
            try {
                element.click();
                //return element;

            } catch (NoSuchElementException e) {
                System.out.println("Cannot find element by " + locator + " in 5 tries");
                System.out.println(e);
            }
        }

        Assert.fail("Element " + locator + " was not found");
    }

    public void waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.click();
        //return element;

    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;

    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeoutInSeconds) {

        By by = this.getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.clear();
        return element;

    }

    public void swipeUp(int timeOfSwipe) {

        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);

            action
                    .press(x, start_y)
                    .waitAction(timeOfSwipe)
                    .moveTo(x, end_y)
                    .release()
                    .perform();
        }
        else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }


    public void swipeUpQuick() {

        swipeUp(200);

    }

    public void scrollWebPageUp() {

        if (Platform.getInstance().isMw()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)");
        } else {
            System.out.println("Method scrollWebPageUp() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String errorMessage, int maxSwipes) {

        int alreadySwiped = 0;

        WebElement element = this.waitForElementPresent(locator, errorMessage);

        while(!this.isElementLocatedOnTheScreen(locator)) {
            scrollWebPageUp();
            ++alreadySwiped;
        }
        if (alreadySwiped > maxSwipes) {
            Assert.assertTrue(errorMessage, element.isDisplayed());
        }

    }


    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {

        int alreadySwiped = 0;
        By by = this.getLocatorByString(locator);

        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Cannot find element by swiping up \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }

    }

    public void swipeUpTillElementAppear(String locator, String errorMessage, int maxSwipes) {

        int alreadySwiped = 0;

        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (alreadySwiped > maxSwipes) {
                Assert.assertTrue(errorMessage, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            alreadySwiped++;
        }

    }

    public boolean isElementLocatedOnTheScreen(String locator) {

        int elementLocationByY = this.waitForElementPresent(locator, "Cannot find element by locator", 1).getLocation().getY();
        if (Platform.getInstance().isMw()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object jsResult = JSExecutor.executeScript("return window.pageYOffset");
            elementLocationByY -= Integer.parseInt(jsResult.toString());
        }
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;

    }

    public void clickElementToTheRightUpperCorner(String locator, String errorMessage){

        WebElement element = this.waitForElementPresent(locator + "/..", errorMessage);
        int rightX = element.getLocation().getX();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;
        int width = element.getSize().getWidth();

        int pointToClickX = (rightX + width) - 3;
        int pointToClickY = middleY;

        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.tap(pointToClickX, pointToClickY).perform();
        } else {
            System.out.println("Method clickElementToTheRightUpperCorner() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }


    public void swipeElementToLeft(String locator, String errorMessage) {

        WebElement element = waitForElementPresent(locator, errorMessage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        if (driver instanceof AppiumDriver) {
            TouchAction action = new TouchAction((AppiumDriver) driver);
            action.press(rightX, middleY);
            action.waitAction(500);
            if (Platform.getInstance().isAndroid()){
                action.moveTo(leftX, middleY);
            } else {
                int offsetX = (-1 * element.getSize().getWidth());
                action.moveTo(offsetX, 0);
            }

            action.release();
            action.perform();
        } else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }

    }

    public int getAmountOfElements(String locator) {

        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();

    }

    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts) {

        int currentAttempts = 0;
        boolean needMoreAttempts = true;

        while(needMoreAttempts) {
            try {
                CoreTestCase.waitInSeconds(1);
                this.waitForElementAndClick(locator, errorMessage, 5);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (currentAttempts > amountOfAttempts) {
                    this.waitForElementAndClick(locator, errorMessage, 5);
                }
            }
            ++ currentAttempts;
        }

    }


    public boolean isElementPresent(String locator){
        return getAmountOfElements(locator) > 0;
    }


    public void assertElementNotPresent(String locator, String errorMessage) {

        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {

            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }

    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeOutInSeconds) {

        WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
        return element.getAttribute(attribute);

    }

    private By getLocatorByString(String locatorWithType) {

        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if (byType.equals("xpath")){
            return By.xpath(locator);
        }
        else if (byType.equals("id")){
            return By.id(locator);
        }
        else if (byType.equals("css")){
            return By.cssSelector(locator);
        }
        else {throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);}

    }

}
