import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.util.List;


public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilites = new DesiredCapabilities();

        capabilites.setCapability("device-orientation", "PORTRAIT");  //Вводим вот эту capability для правильной ориентации экрана при старте приложения
        capabilites.setCapability("platformName", "Android");
        capabilites.setCapability("deviceName", "AndroidTestDevice");
        capabilites.setCapability("platformVersion", "8.0");
        capabilites.setCapability("automationName", "Appium");
        capabilites.setCapability("appPackage", "org.wikipedia");
        capabilites.setCapability("appActivity", ".main.MainActivity");
        // capabilites.setCapability("app", "/Users/apalnov/Desktop/AutoTests/avt-mob-6/Lesson-4/Lesson_project/JavaAppiumAutomation/apks/org.wikipedia.apk"); // MacOS
        capabilites.setCapability("app","E:\\Avt-mob-6\\AutoTests\\avt-mob-6\\Lesson-4\\Task-3\\JavaAppiumAutomation\\apks\\org.wikipedia.apk"); // Windows

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilites);

    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults() {

        driver.rotate(ScreenOrientation.PORTRAIT);   // Перед началом теста приводим экран в нужную ориентацию

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'search Wikipedia' input",
                5);

        String searchLine = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Java article searching by: '" + searchLine + "'",
                15);

        String titleBeforeRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of acticle",
                5);

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String titleAfterRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of acticle",
                5);

        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleBeforeRotation,
                titleAfterRotation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String titleAfterSecondRotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of acticle",
                5);

        Assert.assertEquals(
                "Article title have been changed after rotation",
                titleBeforeRotation,
                titleAfterSecondRotation);

    }

    @Test
    public void testSearchActicleInBackgound() {


        driver.rotate(ScreenOrientation.PORTRAIT); // Перед началом следующего теста приводим экран в нужную ориентацию

        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Java article",
                5);

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot article after returning from background",
                5);

    }




// ---------------------------------------------------------------------------------------------------------------------
// Test methods

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by));

    }

    private WebElement waitForElementPresent(By by, String errorMessage) {

        return waitForElementPresent(by, errorMessage, 5);

    }

    private void waitForElementAndClick(By by, String errorMessage) {

        WebElement element = waitForElementPresent(by, errorMessage, 5);


        for (int i = 1; i <= 5; i++) {
            try {
                element.click();
                //return element;

            } catch (NoSuchElementException e) {
                System.out.println("Cannot find element by " + by + " in 5 tries");
                System.out.println(e);
             }
        }

        Assert.fail("Element " + by + " was not found");
    }

    private void waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        //return element;

    }



    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;

    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;

    }

    protected void swipeUp(int timeOfSwipe) {

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

    protected void swipeUpQuick() {

        swipeUp(200);

    }

    protected void swipeUpToFindElements(By by, String errorMessage, int maxSwipes) {

        int alreadySwiped = 0;

        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up \n" + errorMessage, 0);
                return;
            }
        swipeUpQuick();
        ++alreadySwiped;
        }

    }

    protected void swipeElementToLeft(By by, String errorMessage) {

        WebElement element = waitForElementPresent(by, errorMessage, 10);

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

    private int getAmountOfElements(By by) {

        List elements = driver.findElements(by);
        return elements.size();

    }

    private void assertElementNotPresent(By by, String errorMessage) {

        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {

            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }

    }

    private String waitForElementAndGetAttribute(By by, String attribute, String errorMessage, long timeOutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeOutInSeconds);
        return element.getAttribute(attribute);

    }



}