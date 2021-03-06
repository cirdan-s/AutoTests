package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
        SEARCH_INPUT = "xpath://XCUIElementTypeSearchField[@value='Search Wikipedia']";
        SEARCH_CANCEL_BUTTON = "id:Close";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeLink";
        EMPTY_RESULT_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        CLEAR_SEARCH_BUTTON = "xpath://XCUIElementTypeButton[@name='clear mini']";
    }

    public iOSSearchPageObject(AppiumDriver driver) {

        super(driver);

    }

}
