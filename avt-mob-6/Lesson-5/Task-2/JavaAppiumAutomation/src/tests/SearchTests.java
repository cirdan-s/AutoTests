package tests;

import lib.CoreTestCase;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearchForElementByTitleAndDescription(){

        String  searchExpression = "Arsenal",
                searchTitle1 = "Arsenal",
                searchDescription1 = "Place for arms and ammunition",
                searchTitle2 = "Arsenal F.C.",
                searchDescription2 = "Association football club based in Highbury, London, England",
                searchTitle3 = "Arsène Wenger",
                searchDescription3 = "French footballer and manager";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchExpression);
        SearchPageObject.waitForSearchResult(searchTitle1);
        SearchPageObject.waitForElementByTitleAndDescription(searchTitle1, searchDescription1);
        SearchPageObject.waitForElementByTitleAndDescription(searchTitle2, searchDescription2);
        SearchPageObject.waitForElementByTitleAndDescription(searchTitle3, searchDescription3);

    }


    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();

    }


    @Test
    public void testAmountOfNotEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        String searchLine = "Linkin Park Discography";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchLine);
        int amountOfSearchResults = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We've found too few results!",
                amountOfSearchResults > 0);

    }

    @Test
    public void testAmountOfEmptySearch() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();

        String searchLine = "werf ffqrv fvfwv fv vfv";

        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();

    }

    @Test
    public void testCancelSearchEx3Ref() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        String searchString = "Navy";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchString);

        SearchPageObject.waitForSearchResult(searchString);
        SearchPageObject.cancelSearchAndCheckIfPageIsCleared();

    }

    @Test
    public void testSearchResultAnalyst() {

        String searchString = "Navy";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchString);
        SearchPageObject.waitForSearchResult(searchString);



    }

}