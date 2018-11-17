package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import junit.framework.TestCase;
import sun.security.krb5.internal.crypto.Des;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";


    @Override
    protected void setUp() throws Exception {

        super.setUp();
        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();

        driver = new AndroidDriver(new URL(AppiumURL), capabilities);
        this.rotateScreenPortrait();
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

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {

        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)){

            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "AndroidTestDevice");
            capabilities.setCapability("automationName", "Appium");
            capabilities.setCapability("appPackage", "org.wikipedia");
            capabilities.setCapability("appActivity", ".main.MainActivity");
            capabilities.setCapability("app", "/Users/apalnov/Documents/AutoTests/avt-mob-6/Lesson-6/Lesson_project/JavaAppiumAutomation/apks/org.wikipedia.apk"); // MacOS
           // capabilites.setCapability("app","E:\\Avt-mob-6\\AutoTests\\avt-mob-6\\Lesson-5\\Lesson_project\\JavaAppiumAutomation\\apks\\org.wikipedia.apk"); // Windows
            capabilities.setCapability("platformVersion", "7.1");

        }

        else if (platform.equals(PLATFORM_IOS)) {

            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("deviceName", "iPhone SE");
            capabilities.setCapability("app", "/Users/apalnov/Documents/AutoTests/avt-mob-6/Lesson-6/Lesson_project/JavaAppiumAutomation/apks/Wikipedia.app"); // MacOS
            capabilities.setCapability("platformVersion", "11.4");

        }

        else {
            throw new Exception("Cannot get platform from env variable. Platform value: " + platform);
        }

        return capabilities;

    }

}
