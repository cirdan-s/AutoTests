package lib;

import io.appium.java_client.AppiumDriver;
import lib.ui.WelcomePageObject;
import org.openqa.selenium.ScreenOrientation;
import junit.framework.TestCase;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
        this.skipWelcomePageForIOSApp();
    }

    @Override
    protected void tearDown() throws Exception {

        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait(){

        driver.rotate(ScreenOrientation.PORTRAIT);

    }

    protected void rotateScreenLandscape(){

        driver.rotate(ScreenOrientation.LANDSCAPE);

    }

    protected void backgrounApp(int seconds){

        driver.runAppInBackground(seconds);

    }

    private void skipWelcomePageForIOSApp() {

        if(Platform.getInstance().isIOS()) {
            WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);
            WelcomePageObject.clickSkip();
        }

    }

    public static void waitInSeconds(int secondsToWait){

        int waitIntervalMillis = secondsToWait * 1000;
        try {
            Thread.sleep(waitIntervalMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
