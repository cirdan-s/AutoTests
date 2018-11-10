package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search…')]",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            EMPTY_RESULT_RESULT_ELEMENT = "//*[@text='No results found']",
            SEARCH_RESULT_ITEM_TITLE = "org.wikipedia:id/page_list_item_title",
            SEARCH_ELEMENT_TITLE_DESCRIPTION_TPL = "//android.widget.TextView[@text='{DESCRIPTION}']/preceding-sibling::android.widget.TextView[@text='{TITLE}']";



    public SearchPageObject(AppiumDriver driver) {

        super(driver);

    }

    /* TEMPLATES METHODS */

    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getSearchResultElementXpath(String searchResultTitle, String searchResultDescription){

        return SEARCH_ELEMENT_TITLE_DESCRIPTION_TPL.replace("{TITLE}", searchResultTitle).replace("{DESCRIPTION}", searchResultDescription);

    }


    /* TEMPLATES METHODS */

    public void waitForCancelButtonToAppear(){

        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);

    }


    public void waitForCancelButtonToDisappear(){

        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);

    }

    public void clickCancelSearch(){

        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button");
    }


    public void initSearchInput(){

        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT),"Cannot find and click search init element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INPUT),"Cannot find search input after clicking search init element");
    }

    public void typeSearchLine(String searchLine){

        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine, "Cannot find and type into search input", 5);

    }

    public void waitForSearchResult(String substring){

        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with substring" + substring);

    }

    public void clickByArticleWithSubstring(String substring){

        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(searchResultXpath), "Cannot find and click on search result with substring " + substring, 10);

    }

    public int getAmountOfFoundArticles(){

        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15);

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));

    }

    public void waitForEmptyResultsLabel() {

        this.waitForElementPresent(By.xpath(EMPTY_RESULT_RESULT_ELEMENT), "Cannot find empty result element", 15);

    }

    public void assertThereIsNoResultOfSearch(){

        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT),"We supposed not to find any results");

    }

    public void cancelSearchAndCheckIfPageIsCleared(){

        ArrayList<WebElement> elements = searchForElements(By.id(SEARCH_RESULT_ITEM_TITLE));
        //System.out.println("Search returned: " + elements.size() + " articles with following headers");

        for(WebElement element : elements) {

            String title = element.getText();
            //System.out.println(title);
        }

        waitForElementAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find X to cancel Search",
                10);

        waitForElementNotPresent(
                By.id(SEARCH_RESULT_ITEM_TITLE),
                "Search is not cleared yet",
                15);
    }



    private ArrayList<WebElement> searchForElements (By by) {


        waitForElementPresent(by, "No elements were found", 20);
        List<WebElement> elements = driver.findElements(by);
        ArrayList<WebElement> result = new ArrayList<WebElement>(elements);
        return result;
    }

    public void waitForElementByTitleAndDescription(String title, String description){

        String elementId = getSearchResultElementXpath(title, description);
        //System.out.println("Returned element xpath: " + elementId);
        waitForElementPresent(By.xpath(elementId), "Cannot find element by title and description", 15);

    }


}
