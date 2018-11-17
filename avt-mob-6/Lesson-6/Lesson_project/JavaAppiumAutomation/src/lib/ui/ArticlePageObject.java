package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE = "id:org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "xpath://*[@text='View page in browser']",
            OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://*[@text='Add to reading list']",
            ADD_TO_MY_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button",
            MY_LIST_INPUT = "id:org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "xpath://*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";



    public ArticlePageObject(AppiumDriver driver) {

        super(driver);

    }

    public WebElement waitForTitleElement(){

       return this.waitForElementPresent(TITLE, "Cannot find article title on page", 15);

    }

    public String getArticleTitle(){

        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");

    }

    public void swipeToFooter(){

        this.swipeUpToFindElement(
                FOOTER_ELEMENT,
                "Cannot find the end of article",
                20
        );

    }

    public void addArticleToMyList(String nameOfFolder) {

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

    public void closeArticle() {

        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );

    }

}
