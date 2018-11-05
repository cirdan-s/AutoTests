package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {

    private static final String
            NAME_OF_FOLDER_IN_LIST_TPL = "//*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

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
                By.xpath(folderNameXpath),
                "Cannot find folder by name" + nameOfFolder,
                5
        );

    }

    public void waitForArticleToAppearByTitle(String articleTitle) {

        String articleXpath = getFolderXpathByName(articleTitle);
        this.waitForElementPresent(
                By.xpath(articleXpath),
                "Cannot find saved article by title " + articleTitle,
                15
        );
    }



    public void waitForArticleToDisappearByTitle(String articleTitle) {

        String articleXpath = getFolderXpathByName(articleTitle);
        this.waitForElementNotPresent(
                By.xpath(articleXpath),
                "Saved article still present with title " + articleTitle,
                15
        );
    }


    public void swipeByArticleToDelete(String articleTitle) {

        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getSavedArticleXpathByTitle(articleTitle);
        this.swipeElementToLeft(
                By.xpath(articleXpath),
                "Cannot delete saved article");

        this.waitForArticleToDisappearByTitle(articleTitle);


    }

    public void openArticleInListByName(String articleName){

        String articleTitleXpath = getSavedArticleXpathByTitle(articleName);
        this.waitForElementAndClick(
                By.xpath(articleTitleXpath),
                "Cannot find article by name in My List " + articleName,
                5
        );

    }


}
