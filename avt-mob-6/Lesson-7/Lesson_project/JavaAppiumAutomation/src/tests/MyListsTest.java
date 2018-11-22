package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTest extends CoreTestCase {

    private static final String NAME_OF_FOLDER = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String articleTitle = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(NAME_OF_FOLDER);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
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
                searchStringFirst = "Object-oriented programming language",
                articleTitle = "Java (programming language)",
                searchLineSecond = "Chivas Regal",
                searchStringSecond = "Blended Scotch Whisky produced by Chivas Brothers";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchLineFirst);
        SearchPageObject.clickByArticleWithSubstring(searchStringFirst);

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(NAME_OF_FOLDER);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }

        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        if (Platform.getInstance().isIOS()){
            SearchPageObject.clearSearchInput();
        }

        SearchPageObject.typeSearchLine(searchLineSecond);
        SearchPageObject.clickByArticleWithSubstring(searchStringSecond);

        String secondArticleTitle = ArticlePageObject.waitForTitleAndGetText();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(NAME_OF_FOLDER);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
        MyListPageObject.openFolderByName(NAME_OF_FOLDER);

        MyListPageObject.openFolderByName(NAME_OF_FOLDER);
        MyListPageObject.swipeByArticleToDelete(articleTitle);

        MyListPageObject.openArticleInListByName(secondArticleTitle);

        String articleTitleInList = ArticlePageObject.waitForTitleAndGetText();
        System.out.println("Заголовок статьи в списке: " + articleTitleInList);


        ArticlePageObject.assertCompareArticlesTitle(secondArticleTitle, secondArticleTitle);

        System.out.println("Actual result: " + secondArticleTitle);
        System.out.println("Expected result: " + articleTitleInList);

    }

}
