import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainClass {

    private ChromeDriver driver;


    @Before
    public void initialSetup() throws Exception {

        driver = new ChromeDriver();
        driver.get("https://api.fitforbucks.com/admins/sign_in");
        waitForElementAndSendKeys(
                By.id("admin_email"),
                "apalnov@shakuro.com" ,
                "Admin email textbox wasn't find",
                5);
        waitForElementAndSendKeys(
                By.id("admin_password"),
                "test12345",
                "Admin password textbox wasn't find",
                1);
        waitForElementAndClick(By.xpath(
                "//button[@type='submit']"),
                "Admin password textbox wasn't find",
                1);

        }

    @After
    public void tearDown() { driver.quit(); }


    @Test
    public void testCreateNewCompany() {

        waitForElementPresent(
                By.xpath("//strong[@class='font-bold']"),
                "Looks like login wasn't successful",
                5);

        waitForElementAndClick(
                By.xpath("//span[contains(text(),'Companies')]"),
                "Cannot find Company link",
                1);

        waitForElementPresent(
                By.xpath("//h2[contains(text(),'Companies')]"),
                "Looks like we're not on Companies page",
                5);

        waitForElementAndClick(
                By.xpath("//a[@class='btn btn-primary pull-right']"),
                "New company button unavailable",
                1);

        waitForElementPresent(
                By.xpath("//h2[contains(text(),'New company')]"),
                "Looks like we're not on New Company page",
                5);

        waitForElementAndClick(
                By.xpath("//input[@value='Create Company']"),
                "Cannot find 'Create Company' button",
                1);

        waitForElementPresent(
                By.xpath("//div[@class='string required company_name has-error']//span[@class='help-block'][contains(text(),\"can't be blank\")]"),
                "Missing error message",
                5);


/*        try {
        synchronized (driver) { driver.wait(5000); } }
        catch (InterruptedException e) {System.out.println("InterruptedException");}
*/
    }

    @Test




    private WebElement waitForElementPresent(By by,String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));

    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;

    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;

    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

}
