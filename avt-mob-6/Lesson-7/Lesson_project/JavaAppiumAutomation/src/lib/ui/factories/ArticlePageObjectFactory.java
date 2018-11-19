package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.android.AndroidArcticlePageObject;
import lib.ui.ios.iOSArticlePageObject;

public class ArticlePageObjectFactory {

    public static ArticlePageObject get(AppiumDriver driver){

        if (Platform.getInstance().isAndroid()){
            return new AndroidArcticlePageObject(driver);
        } else {
            return new iOSArticlePageObject(driver);
        }

    }

}
