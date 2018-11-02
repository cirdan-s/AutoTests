package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import junit.framework.TestCase;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";


    @Override
    protected void setUp() throws Exception {

        super.setUp();
        DesiredCapabilities capabilites = new DesiredCapabilities();

        capabilites.setCapability("platformName", "Android");
        capabilites.setCapability("deviceName", "AndroidTestDevice");
        capabilites.setCapability("automationName", "Appium");
        capabilites.setCapability("appPackage", "org.wikipedia");
        capabilites.setCapability("appActivity", ".main.MainActivity");
        capabilites.setCapability("app", "/Users/apalnov/Desktop/AutoTests/avt-mob-6/Lesson-5/Lesson_project/JavaAppiumAutomation/apks/org.wikipedia.apk"); // MacOS
//      capabilites.setCapability("app","E:\\Avt-mob-6\\AutoTests\\avt-mob-6\\Lesson-4\\Lesson_project\\JavaAppiumAutomation\\apks\\org.wikipedia.apk"); // Windows

        capabilites.setCapability("noReset", "true");
        capabilites.setCapability("udid", "b66fb853");
        capabilites.setCapability("platformVersion", "4.4.2");

        driver = new AndroidDriver(new URL(AppiumURL), capabilites);
    }

    @Override
    protected void tearDown() throws Exception {

        driver.quit();
        super.tearDown();
    }


}
