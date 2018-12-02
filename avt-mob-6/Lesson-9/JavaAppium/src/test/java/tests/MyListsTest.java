package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import javax.swing.plaf.metal.MetalBorders;

public class MyListsTest extends CoreTestCase {

    private static final String NAME_OF_FOLDER = "Learning programming";
    private static final String
            login = "apalnov",
            password = "test3000";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String articleTitle = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyListForTheFirstTime(NAME_OF_FOLDER);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }

        if (Platform.getInstance().isMw()) {
            CoreTestCase.waitInSeconds(5);
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            CoreTestCase.waitInSeconds(1);
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login",
                    articleTitle,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticleToMySaved();
            CoreTestCase.waitInSeconds(3);
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(NAME_OF_FOLDER);
        }

        MyListPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testSaveTwoArticleToMyListAndDeleteOne() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String searchLineFirst = "Java",
                searchStringFirst = "bject-oriented programming language",
                articleTitle = "Java (programming language)",
                //searchLineSecond = "Chivas Regal",
                //searchStringSecond = "lended Scotch Whisky produced by Chivas Brothers";
                searchLineSecond = "White House",
                searchStringSecond = "fficial residence and workplace of the President of the United States";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchLineFirst);
        SearchPageObject.clickByArticleWithSubstring(searchStringFirst);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyListForTheFirstTime(NAME_OF_FOLDER);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }

        if (Platform.getInstance().isMw()){
            CoreTestCase.waitInSeconds(5);
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            CoreTestCase.waitInSeconds(1);
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login",
                    articleTitle,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticleToMySaved();
            CoreTestCase.waitInSeconds(3);
        }

        ArticlePageObject.closeArticle();

        if (Platform.getInstance().isIOS()){
            this.backgrounApp(10);
        }

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchLineSecond);
        SearchPageObject.clickByArticleWithSubstring(searchStringSecond);

        String secondArticleTitle = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(NAME_OF_FOLDER);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.closeArticle();
            NavigationUI.clickMyLists();
        }

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(NAME_OF_FOLDER);
        } else if (Platform.getInstance().isIOS()){
            ArticlePageObject.closeArticleNoPopup();
            CoreTestCase.waitInSeconds(5);
            NavigationUI.clickMyLists();
        } else if (Platform.getInstance().isMw()) {
            NavigationUI.openNavigation();
            NavigationUI.clickMyLists();
        }

        MyListPageObject.swipeByArticleToDelete(articleTitle);

        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openArticleInListByName(secondArticleTitle);
        } else if (Platform.getInstance().isIOS()){
            SearchPageObject.clickByArticleWithSubstring(searchStringSecond);
        }

        String articleTitleInList;

        if (Platform.getInstance().isAndroid()) {
            articleTitleInList = ArticlePageObject.waitForTitleAndGetText();
        } else if (Platform.getInstance().isIOS()){
            articleTitleInList = ArticlePageObject.getArticleTitle();
        } else {
            articleTitleInList = ArticlePageObject.mwGetArticleTitleInWatchlist();
        }

        System.out.println("Заголовок статьи в списке: " + articleTitleInList);

        ArticlePageObject.assertCompareArticlesTitle(secondArticleTitle, secondArticleTitle);

        System.out.println("Actual result: " + secondArticleTitle);
        System.out.println("Expected result: " + articleTitleInList);

        if (Platform.getInstance().isMw()) {
            MyListPageObject.clearMyListsAfterTest(secondArticleTitle);
        }

    }

}