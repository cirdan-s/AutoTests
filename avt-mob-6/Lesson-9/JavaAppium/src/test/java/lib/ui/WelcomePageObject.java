package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {

    private static final String
        STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia",
        STEP_NEW_WAYS_TO_EXPLORE_TEXT = "id:Learn more about Wikipedia",
        STEP_ADD_OR_EDIT_PREFFERRED_LANG_LINK = "id:Add or edit preferred languages",
        STEP_LEARN_MORE_ABOUT_DATA_COLLECTED = "id:Learn more about data collected",
        NEXT_LINK = "Next",
        GET_STARTED_BUTTON = "Get started",
        SKIP = "id:Skip";


    public WelcomePageObject(RemoteWebDriver driver){

        super(driver);

    }

    public void waitForLearnMoreLink(){

        this.waitForElementPresent(STEP_LEARN_MORE_LINK, "Cannot find 'Learn more about Wikipedia' link", 10);

    }

    public void waitForNewWayToExploreText(){

        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE_TEXT, "Cannot find 'New ways to explore' text", 10);

    }

    public void waitForAddOrEditPrefferedLangText(){

        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFFERRED_LANG_LINK, "Cannot find 'Add or edit preferred languages' text", 10);

    }

    public void waitForLearnForDataCollectedText(){

        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTED, "Cannot find 'Learn more about data collected' text", 10);

    }

    public void clickNextButton(){

        this.waitForElementAndClick(NEXT_LINK, "Cannot find 'Next' button", 10);

    }

    public void clickGetStartedButton(){

        this.waitForElementAndClick(GET_STARTED_BUTTON, "Cannot find 'Get started' button", 10);

    }

    public void clickSkip() {

        this.waitForElementAndClick(SKIP, "Cannot find and click Skip button", 5);

    }


}
