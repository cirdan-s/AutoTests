package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.CoreTestCase;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
            TITLE,
            TITLE_OF_SECOND_ARTICLE,
            FOOTER_ELEMENT,
            OPTIONS_BUTTON,
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            ADD_TO_MY_LIST_OVERLAY,
            MY_LIST_INPUT,
            MY_LIST_OK_BUTTON,
            CLOSE_ARTICLE_BUTTON,
            TITLE_ID,
            CLOSE_OVERLAY_BUTTON,
            MY_LIST_NAME_TPL;

    /* TEMPLATES METHODS */
    private static String getNameOfFolderXpath(String nameOfFolder){

        return MY_LIST_NAME_TPL.replace("{nameOfFolder}", nameOfFolder);

    }
    /* TEMPLATES METHODS */


    public ArticlePageObject(AppiumDriver driver) {

        super(driver);

    }

    public WebElement waitForTitleElement(){

        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 15);

    }

    public String getArticleTitle(){

        WebElement titleElement = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return titleElement.getAttribute("text");
        } else {
            return titleElement.getAttribute("name");
        }

    }

    public void swipeToFooter(){

        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    100
            );
        } else {
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    100);
        }

    }

    public void addArticleToMyListForTheFirstTime(String nameOfFolder) {

        CoreTestCase.waitInSeconds(5);
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        try {Thread.sleep(10000);}
        catch (InterruptedException exception) {
            System.out.println(exception);
        }

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find GOT IT button",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_INPUT,
                "Cannot find Reading list name input",
                5);


        this.waitForElementAndSendKeys(
                MY_LIST_INPUT,
                nameOfFolder,
                "Cannot put text into article folder input",
                5);

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );

    }

    public void addArticleToMyList(String nameOfFolder) {

        String nameOfFolderXpath = getNameOfFolderXpath(nameOfFolder);

        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Add to my list option is not found"
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find options to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                nameOfFolderXpath,
                "Cannot click on folder with name: " + nameOfFolder,
                5
        );

    }

    public void addArticleToMySaved() {

        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot add article to reading list", 5);

    }

    public void closeArticle() {

        if (Platform.getInstance().isAndroid()){
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5
            );
        } else {
            this.waitForElementAndClick(
                    CLOSE_OVERLAY_BUTTON,
                    "Cannot close iOS overlay 'Sync your saved articles'",
                    5
            );
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5
            );
        }

    }

    public void closeArticleNoPopup() {

            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5
            );

    }

    public String waitForTitleAndGetText() {

        CoreTestCase.waitInSeconds(5);
        WebElement element = waitForElementPresent(TITLE, "Cannot find article title", 10);
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
