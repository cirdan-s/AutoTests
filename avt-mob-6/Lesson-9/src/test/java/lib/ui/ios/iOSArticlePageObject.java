package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class iOSArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "id:Java (programming language)";
        //TITLE = "id:Chivas Regal";
        TITLE_OF_SECOND_ARTICLE = "id:Chivas Regal";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        CLOSE_OVERLAY_BUTTON = "id:places auth close";
        TITLE_ID = "";
    }

    public iOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }



}
