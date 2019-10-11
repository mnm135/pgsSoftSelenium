package pageobjects;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Task4Page extends BasePage {

    private static final String URL = "https://testingcup.pgs-soft.com/task_4";

    @FindBy(xpath = "//button[contains(text(), 'APLIKUJ')]")
    public WebElement applyButton;
    @FindBy(xpath = "//input[@name='name']")
    public WebElement nameInput;
    @FindBy(xpath = "//input[@name='email']")
    public WebElement emailInput;
    @FindBy(xpath = "//input[@name='phone']")
    public WebElement phoneInput;
    @FindBy(id = "save-btn")
    public WebElement saveButton;
    @FindBy(xpath = "//h1[contains(text(),'Wiadomość została wysłana')]")
    public WebElement applicationSentMessage;
    @FindBy(xpath = "//span[contains(text(),'Nieprawidłowy email')]")
    public WebElement wrongEmailError;
    @FindBy(xpath = "//span[contains(text(),'Zły format telefonu')]")
    public WebElement wrongPhoneError;


    public Task4Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }

    @Step("Fill and save user form")
    public void fillAndSaveTheForm(String name, String phone, String email) {
        nameInput.sendKeys(name);
        phoneInput.sendKeys(phone);
        emailInput.sendKeys(email);
        saveButton.click();
    }

    @Step("Navigate to newly open window")
    public void switchToNewWindow() {
        driver.close();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        driver.manage().window().maximize();
        driver.switchTo().frame(0);
        waitForMultipleElements(List.of(nameInput, phoneInput, emailInput, saveButton));
    }

    @Step("Verify user form is displayed")
    public void verifyFormIsDisplayed() {
        Assertions.assertTrue(nameInput.isDisplayed());
        Assertions.assertTrue(phoneInput.isDisplayed());
        Assertions.assertTrue(emailInput.isDisplayed());
        Assertions.assertTrue(saveButton.isDisplayed());
    }

    @Step("Click apply button")
    public void clickApplyButton() {
        applyButton.click();
    }
}