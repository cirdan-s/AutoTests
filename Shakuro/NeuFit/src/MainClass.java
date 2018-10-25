import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainClass {

    private ChromeDriver driver;


    @Before
    public void initialSetup() throws Exception {

        driver = new ChromeDriver();
        driver.get("https://neufitangularpoc.azurewebsites.net/login");
    }

    @After
    public void tearDown() { driver.quit(); }


    @Test
    public void testCreateNewClient() {

        String clientFirstName, clientLastName, clientStreetLine1, clientStreetLine2, clientZipCode, clientCity, clientState, clientDob;
        String dayOfBirth, monthOfBirth, yearOfBirth;
        Random rand = new Random();

// Присваиваем значения для данных Клиента
        Faker faker = new Faker();
        clientFirstName = faker.name().firstName();
        clientLastName = faker.name().lastName();
        clientStreetLine1 = faker.address().streetName();
        clientStreetLine2 = faker.address().secondaryAddress();
        clientCity = faker.address().city();
        clientState = faker.address().state();
        clientZipCode = faker.address().zipCode();
        dayOfBirth = Integer.toString(rand.nextInt(28) + 1);
        monthOfBirth = Integer.toString(rand.nextInt(12) + 1);
        yearOfBirth = Integer.toString(rand.nextInt(90) + 1930);
        clientDob = monthOfBirth + "/" + dayOfBirth + "/" + yearOfBirth;


        waitForElementAndClick(
                By.xpath("//span[contains(text(),'New Client Registration')]"),
                "Cannot find 'New Client Registration' button",
                5);

        waitForElementPresent(
                By.xpath("//label[contains(text(),'Please complete the following for the person recei')]"),
                "We do not see new client registration fields yet",
                5);

        waitForElementAndSendKeys(
                By.xpath("//input[@id='mat-input-0']"),
                clientFirstName,
                "Failed to find any First Name field",
                5);

        waitForElementAndSendKeys(
                By.xpath("//input[@id='mat-input-1']"),
                clientLastName,
                "Failed to find any Last Name field",
                5);

        sendKeysIntoElement(
                By.xpath("//input[@id='mat-input-2']"),
                clientDob,
                "Cannot find DOB field");

        clickOnElement(
                By.xpath("//button[@type='submit']"),
                "Cannot find Submit button");

        waitForElementPresent(
                By.xpath("//span[contains(text(),'Please enter your registration code')]"),
                "Looks like we're not on Registration code page",
                10);

        sendKeysIntoElement(
                By.xpath("//div[@class='code']//input[1]"),
                "2",
                "Cannot find 1st field for registration code");

        sendKeysIntoElement(
                By.xpath("//div[@class='code']//input[2]"),
                "0",
                "Cannot find 2nd field for registration code");

        sendKeysIntoElement(
                By.xpath("//div[@class='code']//input[3]"),
                "0",
                "Cannot find 3rd field for registration code");

        sendKeysIntoElement(
                By.xpath("//div[@class='code']//input[4]"),
                "9",
                "Cannot find 4th field for registration code");

        waitForElementAndClick(
                By.xpath("//button[@type='submit']"),
                "Cannot find Submit button",
                5);

        waitForElementPresent(
                By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='*'])[4]/following::label[2]"),
                "Navigation to Client Registration page did not perform",
                15);

       // driver.switchTo().defaultContent();

        waitForElementAndClick(
                By.xpath("//li[8]/div/div/span[2]/label"),
                "Cannot find male radio button",
                5);

        sendKeysIntoElement(
                By.id("input_4_addr_line1"),
                 clientStreetLine1,
                "Cannot find Street Address Line 1");

        sendKeysIntoElement(
                By.id("input_4_addr_line2"),
                clientStreetLine2,
                "Cannot find Street Address Line 2");

        sendKeysIntoElement(
                By.id("input_4_city"),
                clientCity,
                "Cannot find City");

        sendKeysIntoElement(
                By.id("input_4_state"),
                clientState,
                "Cannot find State");

        sendKeysIntoElement(
                By.id("input_4_postal"),
                clientZipCode,
                "Cannot find Zip Code");

        sendKeysIntoElement(
                By.id("input_5_full"),
                "1234567890",
                "Cannot find Client's Primary Contact Number");

/*        sendKeysIntoElement(
                By.id("input_4_postal"),
                clientZipCode,
                "Cannot find Zip Code");

        sendKeysIntoElement(
                By.id("input_4_postal"),
                clientZipCode,
                "Cannot find Zip Code");

        sendKeysIntoElement(
                By.id("input_4_postal"),
                clientZipCode,
                "Cannot find Zip Code");

        sendKeysIntoElement(
                By.id("input_4_postal"),
                clientZipCode,
                "Cannot find Zip Code");

        sendKeysIntoElement(
                By.id("input_4_postal"),
                clientZipCode,
                "Cannot find Zip Code");
*/



        try {
        synchronized (driver) { driver.wait(5000); } }
        catch (InterruptedException e) {System.out.println("InterruptedException");}

    }





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

    private WebElement clickOnElement (By by, String errorMessage) {

        WebElement element = waitForElementPresent(by, errorMessage, 0);
        element.click();
        return element;

    }

    private WebElement sendKeysIntoElement(By by, String value, String errorMessage) {

        WebElement element = waitForElementPresent(by, errorMessage, 0);
        element.sendKeys(value);
        return element;

    }

    private ArrayList<WebElement> searchForElements (By by) {

        waitForElementPresent(by, "No elements were found", 20);
        List<WebElement> elements = driver.findElements(by);
        ArrayList<WebElement> result = new ArrayList<WebElement>(elements);
        return result;
    }


}
