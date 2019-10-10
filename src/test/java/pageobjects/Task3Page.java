package pageobjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Task3Page extends BasePage{

    public static final String URL = "https://testingcup.pgs-soft.com/task_3";

    @FindBy(id = "in-name")
    public WebElement nameInput;
    @FindBy(id = "in-surname")
    public WebElement surnameInput;
    @FindBy(id = "in-notes")
    public WebElement notesInput;
    @FindBy(id = "in-phone")
    public WebElement phoneInput;
    @FindBy(className = "dropdown-toggle")
    public WebElement menuDropdown;

    @FindBy(xpath = "//a[contains(text(),'Formularz')]")
    public WebElement formDropdownItem;
    @FindBy(xpath = "//a[contains(text(),'Przejdź do trybu edycji')]")
    public WebElement editModeDropdownItem;
    @FindBy(id = "save-btn")
    public WebElement saveButton;


    public Task3Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }

    public void enableEditMode() {
        menuDropdown.click();
        formDropdownItem.click();
        editModeDropdownItem.click();
    }

    public void fillTheForm(String name, String surname, String notes, String phone) {
        nameInput.clear();
        nameInput.sendKeys(name);
        surnameInput.clear();
        surnameInput.sendKeys(surname);
        notesInput.clear();
        notesInput.sendKeys(notes);
        phoneInput.clear();
        phoneInput.sendKeys(phone);

        scrollToElement(saveButton);
        saveButton.click();
    }

    public void verifyTheFormIsSavedWithCorrectValues(String name, String surname, String notes, String phone) {
        Assertions.assertEquals(name, nameInput.getAttribute("value"));
        Assertions.assertEquals(surname, surnameInput.getAttribute("value"));
        Assertions.assertEquals(notes, notesInput.getAttribute("value"));
        Assertions.assertEquals(phone, phoneInput.getAttribute("value"));
    }

    public void verifyTheFormIsInReadOnlyMode() {
        Assertions.assertFalse(nameInput.isEnabled());
        Assertions.assertFalse(surnameInput.isEnabled());
        Assertions.assertFalse(notesInput.isEnabled());
        Assertions.assertFalse(phoneInput.isEnabled());
        Assertions.assertFalse(saveButton.isDisplayed());
    }

    public void verifyTheFormIsInEditableMode() {
        Assertions.assertTrue(nameInput.isEnabled());
        Assertions.assertTrue(surnameInput.isEnabled());
        Assertions.assertTrue(notesInput.isEnabled());
        Assertions.assertTrue(phoneInput.isEnabled());
        Assertions.assertTrue(saveButton.isDisplayed());
    }
}
