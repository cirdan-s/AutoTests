package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {
        TITLE = "css:#content h1";
        //TITLE = "id:Chivas Regal";
        TITLE_OF_ARTICLE_IN_WATCHLIST = "css:#content h3";
        TITLE_OF_SECOND_ARTICLE = "xpath://li[@title='Chivas Regal']";
        FOOTER_ELEMENT = "css:footer";
       // OPTIONS_ADD_TO_MY_LIST_BUTTON = "css:#page actions li#ca-watch.mw-ui-icon-mf-watch button";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "xpath://a[@role='button']";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page actions li#ca-watch.mw-ui-icon-mf-watched watched button";
        CLOSE_OVERLAY_BUTTON = "id:places auth close";
        TITLE_ID = "";
    }

    public MWArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
