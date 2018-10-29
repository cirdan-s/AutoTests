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
        capabilites.setCapability("app", "/Users/apalnov/Desktop/AutoTests/avt-mob-6/Lesson-4/Task-1/JavaAppiumAutomation/apks/org.wikipedia.apk"); // MacOS
        //capabilites.setCapability("app","E:\\Avt-mob-6\\AutoTests\\avt-mob-6\\Lesson-4\\Lesson_project\\JavaAppiumAutomation\\apks\\org.wikipedia.apk"); // Windows

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilites);
    }

    @After
    public void tearDown() {
        driver.quit();
    }


    @Test
    public void saveTwoArticleToMyListAndDeleteOne() {

        //1 Нажимаем в строку поиска
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find 'search Wikipedia' input",
                5
        );

        System.out.println("Шаг 1 успешно (Нажимаем в строку поиска)");

        //2 Отправляем поисковый запрос
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        System.out.println("Шаг 2 успешно (Отправляем поисковый запрос)");

        //3 Нажимаем на искомую статью
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Java article",
                5
                );
        System.out.println("Шаг 3 успешно (Нажимаем на искомую статью)");

        waitSeconds(15);

        //4 Нажимаем на меню "три точки"
        waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        System.out.println("Шаг 4 успешно (Нажимаем на меню \"три точки\")");

        //5 Нажимаем на пункт меню "Добавить в список для чтения"
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find options to add article to reading list",
                5
        );

        System.out.println("Шаг 5 успешно (Нажимаем на пункт меню \"Добавить в список для чтения\")");

        //6 Нажимаем кнопку "GOT IT"
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find GOT IT button",
                5
        );

        System.out.println("Шаг 6 успешно (Нажимаем кнопку \"GOT IT\")");

        //7 Удаляем дефолтное название списка для чтения
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find Reading list name input",
                5
        );

        System.out.println("Шаг 7 успешно (Удаляем дефолтное название списка для чтения)");

        String nameOfFolder = "Learning programming";

        //8 Набираем нужное название списка для чтения
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into article folder input",
                5
        );

        System.out.println("Шаг 8 успешно (Набираем нужное название списка для чтения)");

        //9 Нажимаем ОК
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        System.out.println("Шаг 9 успешно (Нажимаем ОК)");

        waitSeconds(15);

        //10 Нажимаем на меню "три точки"
        waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        System.out.println("Шаг 10 успешно (Нажимаем на меню \"три точки\")");

        //11 Нажимаем пункт меню "Similar pages"
        waitForElementAndClick(
                By.xpath("//*[@text='Similar pages']"),
                "Cannot similar pages option",
                5
        );

        System.out.println("Шаг 11 успешно (Нажимаем пункт меню \"Similar pages\")");

        //12 Определяем есть ли в списке хотя бы 1 статья

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/disambig_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitSeconds(5);

        int amountOfSearchResults = getAmountOfElements(
                By.xpath(searchResultLocator)
        );

        assertTrue(
                "We've found too few results!",
                amountOfSearchResults > 0);

        System.out.println("Шаг 12 успешно (Определяем есть ли в списке хотя бы 1 статья)");

        //13 Название первой статьи записываем в переменную
        String similarAcricleTitle = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text",
                "",
                5
        );

        System.out.println("Шаг 13 успешно, название статьи: " + similarAcricleTitle);


        //14 Нажимаем на первую статью из списка
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find button to open article options",
                5
        );

        System.out.println("Шаг 14 успешно (Нажимаем на первую статью из списка)");

        waitSeconds(15);

        swipeUp(3);

        System.out.println("Шаг 14-1 успешно");

/*        //15 Нажимаем на меню "три точки"
        waitForElementAndClick(
                By.xpath("//*[@content-desc='More options']"),
                "Cannot find button to open article options",
                5
        );

        System.out.println("Шаг 15 успешно (Нажимаем на меню \"три точки\")");

        //16 Нажимаем на пункт меню "Добавить в список для чтения"
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find options to add article to reading list",
                5
        );

        System.out.println("Шаг 16 успешно (Нажимаем на пункт меню \"Добавить в список для чтения\")");

        //17 Проверяем есть ли нужный список для чтения
        waitForElementPresent(
                By.xpath("//*[resource-id='org.wikipedia:id/item_title']//*[@text='" + nameOfFolder + "']"),
                "Cannot find reading list to add article",
                5
        );

        System.out.println("Шаг 17 успешно (Проверяем есть ли нужный список для чтения)");
*/

        //New step - Нажимаем на кнопку "Добавить в список для чтения
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_actions_tab_layout']/*[@content-desc='Add this article to a reading list']"),
                "Cannot click on Add to reading list button"

        );


        //18 Выбираем тот же список для чтения, куда уже была добавлена первая статья
        waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot click on reading list",
                5
        );

        System.out.println("Шаг 18 успешно (Выбираем тот же список для чтения, куда уже была добавлена первая статья)");

        //19 Закрываем статью
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link"

        );

        System.out.println("Шаг 19 успешно (Закрываем статью)");

        //20 Переходим к спискам для чтения
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot navigation button to my list",
                5
        );

        System.out.println("Шаг 20 успешно (Переходим к спискам для чтения)");

        //21 Переходим в нужный список для чтения
        waitForElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find created folder",
                5);

        System.out.println("Шаг 21 успешно (Переходим в нужный список для чтения)");

        //22 Удаляем первую добавленную статью
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article"
        );

        System.out.println("Шаг 22 успешно (Удаляем первую добавленную статью)");

        //23 Убеждаемся, что статья удалена
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article",
                5
        );

        System.out.println("Шаг 23 успешно (Убеждаемся, что статья удалена)");

        //24 Открываем вторую сохраненную статью
        waitForElementAndClick(
                By.xpath("//*[@recource-id='org.wikipedia:id/page_list_item_container'"),
                "Cannot click on second acticle title",
                5
        );

        System.out.println("Шаг 24 успешно (Открываем вторую сохраненную статью)");

        //25 Получаем заголовок статьи
        String articleTitleInList = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "text",
                "",
                5
        );

        System.out.println("Шаг 25 успешно, заголовок статьи: " + articleTitleInList);

        //26 Сравниваем с ранее сохраненным заголовком
        Assert.assertEquals(
                "We see unexpected title",
                similarAcricleTitle,
                articleTitleInList
        );

        System.out.println("Шаг 26 успешно (Сравниваем с ранее сохраненным заголовком)");

        System.out.println("Actual result: " + similarAcricleTitle);
        System.out.println("Expected result: " + articleTitleInList);

    }


// ---------------------------------------------------------------------------------------------------------------------
// Test methods

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by));

    }

    private void waitForElementAndClick(By by, String errorMessage) {

        WebElement element = waitForElementPresent(by, errorMessage, 5);


        for (int i = 1; i < 5; i++) {
            try {
                System.out.println("Trying to click");
                element.click();
                System.out.println("Successful click");
                waitSeconds(1);
                return;

            } catch (NoSuchElementException e) {
                System.out.println("Couldn't click!");
                System.out.println("Cannot find element by " + by + " in " + i + " tries");
                System.out.println(e);
                Assert.fail("Element " + by + " was not found");
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
                .waitAction(750)
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

    private void waitSeconds (int secondsToWait) {

        int millisecondsToWait = secondsToWait * 1000;
        try {
            Thread.sleep(millisecondsToWait);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}