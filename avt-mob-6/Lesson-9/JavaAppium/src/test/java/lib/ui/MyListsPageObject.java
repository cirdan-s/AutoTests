package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.CoreTestCase;
import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject {

    protected static String
            NAME_OF_FOLDER_IN_LIST_TPL,
            ARTICLE_BY_TITLE_TPL,
            REMOVE_FROM_SAVED_BUTTON;

    public MyListsPageObject(RemoteWebDriver driver) {

        super(driver);

    }

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String nameOfFolder){

        return NAME_OF_FOLDER_IN_LIST_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleName) {

        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleName);

    }

    private static String getRemoveButtonByTitle(String articleName) {

        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleName);

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

        String articleXpath="";

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMw()) {
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

        String articleXpath="";
        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMw()) {
            articleXpath = getSavedArticleXpathByTitle(articleTitle);
        }
        else {
            articleXpath = "xpath://XCUIElementTypeLink[contains(@name, 'Java (programming language)')][contains(@name, 'Object-oriented programming language')]";
        }
        this.waitForElementNotPresent(
                articleXpath,
                "Saved article still present with title " + articleTitle,
                15
        );
    }


    public void swipeByArticleToDelete(String articleTitle) {

        //this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = "";

        if (Platform.getInstance().isAndroid()) {
            articleXpath = getSavedArticleXpathByTitle(articleTitle);
        } else if (Platform.getInstance().isIOS()) {
            articleXpath = "xpath://XCUIElementTypeLink[contains(@name, 'Java (programming language)')][contains(@name, 'Object-oriented programming language')]";
        }

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    articleXpath,
                    "Cannot delete saved article");
        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            //String removeLocator = "xpath://div[@title='Stop watching']";
            this.waitForElementAndClick(
                    removeLocator,
                    "Cannot click button to remove article from saved",
                    10
            );
        }


        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(articleXpath, "Cannot find saved article");
        }

        if (Platform.getInstance().isMw()) {
            System.out.println("Waiting before page refresh");
            CoreTestCase.waitInSeconds(5);
            driver.navigate().refresh();
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

    public void clearMyListsAfterTest(String articleTitle) {

        String removeLocator = getRemoveButtonByTitle(articleTitle);
        //String removeLocator = "xpath://div[@title='Stop watching']";
        this.waitForElementAndClick(
                removeLocator,
                "Cannot click button to remove article from saved",
                10
        );

        driver.navigate().refresh();
        this.waitForArticleToDisappearByTitle(articleTitle);

    }

}
