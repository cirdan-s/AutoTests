package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class MyListsTest extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String articleTitle = ArticlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";

        ArticlePageObject.addArticleToMyList(nameOfFolder);
        ArticlePageObject.closeArticle();


        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = new MyListsPageObject(driver);
        MyListPageObject.openFolderByName(nameOfFolder);
        MyListPageObject.swipeByArticleToDelete(articleTitle);

    }

    @Test
    public void testSaveTwoArticleToMyListAndDeleteOne() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        String  searchLineFirst = "Java",
                searchStringFirst = "Object-oriented programming language",
                articleTitle = "Java (programming language)",
                searchLineSecond = "Chivas Regal",
                searchStringSecond = "Blended Scotch Whisky produced by Chivas Brothers";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchLineFirst);
        SearchPageObject.clickByArticleWithSubstring(searchStringFirst);

        //waitSeconds(15);

        String nameOfFolder = "Learning programming";

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.addArticleToMyListForTheFirstTime(nameOfFolder);

        ArticlePageObject.closeArticle();

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchLineSecond);
        SearchPageObject.clickByArticleWithSubstring(searchStringSecond);

        String secondArticleTitle = ArticlePageObject.waitForTitleAndGetText();

        ArticlePageObject.addArticleToMyList(nameOfFolder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = new MyListsPageObject(driver);
        MyListPageObject.openFolderByName(nameOfFolder);

        MyListPageObject.openFolderByName(nameOfFolder);
        MyListPageObject.swipeByArticleToDelete(articleTitle);

        MyListPageObject.openArticleInListByName(secondArticleTitle);

        String articleTitleInList = ArticlePageObject.waitForTitleAndGetText();
        System.out.println("Заголовок статьи в списке: " + articleTitleInList);


        ArticlePageObject.assertCompareArticlesTitle(secondArticleTitle, secondArticleTitle);

        System.out.println("Actual result: " + secondArticleTitle);
        System.out.println("Expected result: " + articleTitleInList);

    }


}
