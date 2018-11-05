package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.CoreTestCase;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import static com.gargoylesoftware.htmlunit.WebAssert.assertElementPresent;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            TITLE_XPATH = "//*[@resource-id='org.wikipedia:id/view_page_title_text']",
            FOOTER_ELEMENT = "//*[@text='View page in browser']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            MY_LIST_INPUT = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']",
            MY_LIST_NAME_TPL = "//*[@text='{nameOfFolder}']";


    /* TEMPLATES METHODS */
    private static String getNameOfFolderXpath(String nameOfFolder){

        return MY_LIST_NAME_TPL.replace("{nameOfFolder}", nameOfFolder);

    }
    /* TEMPLATES METHODS */

    public ArticlePageObject(AppiumDriver driver) {

        super(driver);

    }

    public WebElement waitForTitleElement(){

       return this.waitForElementPresent(By.id(TITLE), "Cannot find article title on page", 15);

    }

    public String getArticleTitle(){

        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");

    }

    public void swipeToFooter(){

        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20
        );

    }

    public void addArticleToMyListForTheFirstTime(String nameOfFolder) {

        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        this.waitForElementPresent(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Add to my list option is not found",
                10
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find options to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find GOT IT button",
                5
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_INPUT),
                "Cannot find Reading list name input",
                5);


        this.waitForElementAndSendKeys(
                By.id(MY_LIST_INPUT),
                nameOfFolder,
                "Cannot put text into article folder input",
                5);

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                5
        );

    }

    public void addArticleToMyList(String nameOfFolder) {

        String nameOfFolderXpath = getNameOfFolderXpath(nameOfFolder);

        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                5
        );

        this.waitForElementPresent(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Add to my list option is not found"
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find options to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                By.xpath(nameOfFolderXpath),
                "Cannot click on folder with name: " + nameOfFolder,
                5
        );

    }

    public void closeArticle() {

        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X link",
                5
        );

    }

    public void assertArticleTitlePresentRightAfterPageLoad() {

        try {
            waitForElementPresent(
                    By.xpath(TITLE),
                    "Cannot find article title",
                    0);
            System.out.println("Article title was found on page right after load");
        } catch (TimeoutException e) {
            Assert.fail("Article title was not found on page right after load");
            System.out.println(e.getCause());
        }

    }

    public String waitForTitleAndGetText() {

        CoreTestCase.waitInSeconds(5);
        WebElement element = waitForElementPresent(By.xpath(TITLE_XPATH), "Cannot find article title", 10);
        return element.getAttribute("text");
    }

    public void assertCompareArticlesTitle(String expectedTitle, String actualTitle){

        Assert.assertEquals(
                "We see unexpected title",
                expectedTitle,
                actualTitle
        );

    }

}
