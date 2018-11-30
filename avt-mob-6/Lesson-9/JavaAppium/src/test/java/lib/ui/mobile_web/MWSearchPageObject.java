package lib.ui.mobile_web;


import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:button.cancel";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class,'wikidata-description')][contains(text(),'{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ul.page-list>li.page-summary";
        EMPTY_RESULT_RESULT_ELEMENT = "css:p.without-results";
        CLEAR_SEARCH_BUTTON = "css:button.clear";
    }

    public MWSearchPageObject(RemoteWebDriver driver) {

        super(driver);

    }

}

//div[contains(@class,'wikidata-description')][contains(text(),'object-oriented programming language']