package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.By;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            NAME_OF_FOLDER_IN_LIST_TPL,
            ARTICLE_BY_TITLE_TPL;

    public MyListsPageObject(AppiumDriver driver) {

        super(driver);

    }

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String nameOfFolder){

        return NAME_OF_FOLDER_IN_LIST_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleName) {

        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleName);

    }
    /* TEMPLATES METHODS */



    public void openFolderByName(String nameOfFolder) {

        String folderNameXpath = getFolderXpathByName(nameOfFolder);
        this.waitForElementAndClick(
                folderNameXpath,
                "Cannot find folder by name: " + nameOfFolder,
                5
        );

    }

    public void waitForArticleToAppearByTitle(String articleTitle) {

        String articleXpath;

        if (Platform.getInstance().isAndroid()) {
            articleXpath = getSavedArticleXpathByTitle(articleTitle);
        }
        else {
            articleXpath = "xpath://XCUIElementTypeLink[contains(@name, 'Java (programming language)')][contains(@name, 'Object-oriented programming language')]";
        }
        this.waitForElementPresent(
                articleXpath,
                "Cannot find saved article by title " + articleTitle,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) {

        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.waitForElementNotPresent(
                "xpath://XCUIElementTypeLink[contains(@name, 'Java (programming language)')][contains(@name, 'Object-oriented programming language')]",
                "Saved article still present with title " + articleTitle,
                15
        );
    }


    public void swipeByArticleToDelete(String articleTitle) {

        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = "";

        if (Platform.getInstance().isAndroid()) {
            articleXpath = getSavedArticleXpathByTitle(articleTitle);
        } else if (Platform.getInstance().isIOS()) {
            articleXpath = "xpath://XCUIElementTypeLink[contains(@name, 'Java (programming language)')][contains(@name, 'Object-oriented programming language')]";
        }

        this.swipeElementToLeft(
                articleXpath,
                "Cannot delete saved article");

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(articleXpath, "Cannot find saved article");
        }

        this.waitForArticleToDisappearByTitle(articleTitle);
    }


    public void openArticleInListByName(String articleName){

        String articleTitleXpath = getSavedArticleXpathByTitle(articleName);

        this.waitForElementAndClick(
                articleTitleXpath,
                "Cannot find article by name in My List " + articleName,
                5
        );

    }

}
