import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.apache.xalan.xsltc.dom.SimpleResultTreeImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilites = new DesiredCapabilities();

        capabilites.setCapability("platformName", "Android");
        capabilites.setCapability("deviceName", "AndroidTestDevice");
        capabilites.setCapability("platformVersion", "8.0");
        capabilites.setCapability("automationName", "Appium");
        capabilites.setCapability("appPackage", "org.wikipedia");
        capabilites.setCapability("appActivity", ".main.MainActivity");
        // capabilites.setCapability("app", "/Users/apalnov/Desktop/AutoTests/avt-mob-6/Lesson-4/Lesson_project/JavaAppiumAutomation/apks/org.wikipedia.apk"); // MacOS
        capabilites.setCapability("app","E:\\Avt-mob-6\\AutoTests\\avt-mob-6\\Lesson-4\\Lesson_project\\JavaAppiumAutomation\\apks\\org.wikipedia.apk"); // Windows

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilites);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find ORPL topic",
                15);
    }


    @Test
    public void testCancelSearch() {

        waitForElementAndClick(
             By.id("org.wikipedia:id/search_container"),
             "Cannot find 'search Wikipedia' input",
             5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel Search",
                5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5);

    }

    @Test
    public void testCompareArticleTitle() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Java article",
                5);

        WebElement titleElement = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        String acticleTitle = titleElement.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                acticleTitle);

    }


    @Test
    public void testSwipeArticle() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find Appium article",
                5);

        WebElement titleElement = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15);

        swipeUpToFindElements(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find article swiping up",
                20);

    }


    @Test
    public void saveFirstArticleToMyList() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'search Wikipedia' input"
                );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Java article",
                5
                );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options"
                );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find options to add article to reading list"
                );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find GOT IT button"
                );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find Reading list name input",
                5);

        String nameOfFolder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into article folder input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button"
                );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"
                );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot navigation button to my list"
                );

        waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find created folder"
                );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article");

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article",
                5);

    }

    @Test
    public void testAmountOfNotEmptySearch() {

        String searchLine = "Linkin Park Discography";
        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        int amountOfSearchResults;


        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'search Wikipedia' input");

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Cannot find search input",
                5);

        waitForElementPresent(
                By.xpath(searchResultLocator),
                "Cannot find anything by the request: " + searchLine,
                15);

        amountOfSearchResults = getAmountOfElements(
                By.xpath(searchResultLocator)
        );

        assertTrue(
                "We've found too few results!",
                amountOfSearchResults > 0);

    }

    @Test
    public void testAmountOfEmptySearch() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'search Wikipedia' input");


        String searchLine = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchLine,
                "Cannot find search input",
                5);

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String emptyResultLabel = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "Cannot find empty result label by the request: '" + searchLine + "'",
                15);

        assertElementNotPresent(
                By.xpath(searchResultLocator),
                "We've found some results by request" + " " + searchLine);

    }


    @Test
    public void testChangeScreenOrientationOnSearchResults() {

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