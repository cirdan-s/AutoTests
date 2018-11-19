package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){

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

        TouchAction action = new TouchAction(driver);
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

    public void swipeUpQuick() {

        swipeUp(200);

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
        int screenSizeByY = driver.manage().window().getSize().getHeight();
        return elementLocationByY < screenSizeByY;

    }

    public void swipeElementToLeft(String locator, String errorMessage) {

        WebElement element = waitForElementPresent(locator, errorMessage, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(rightX, middleY)
                .waitAction(500)
                .moveTo(leftX, middleY)
                .release()
                .perform();
    }

    public int getAmountOfElements(String locator) {

        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();

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
        else {throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);}

    }



}
