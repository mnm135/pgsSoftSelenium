package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

public class Task6Page extends BasePage {

    private static final String URL = "https://testingcup.pgs-soft.com/task_6";

    @FindBy(id = "LoginForm__username")
    public WebElement userNameInput;
    @FindBy(id = "LoginForm__password")
    public WebElement passwordInput;
    @FindBy(id = "LoginForm_save")
    public WebElement saveButton;
    @FindBy(id = "logout")
    public WebElement logoutButton;
    @FindBy(xpath = "//a[contains(text(), 'Pobierz')]")
    public WebElement downloadButton;
    @FindBy(xpath = "//*[text()[contains(.,'Nieprawidłowe dane logowania')]]")
    public WebElement incorrectLoginDataMessage;


    public Task6Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }

    public boolean isFileDownloaded(String fileName) {
        File dir = new File("C:\\Users\\Emil\\Downloads");
        //File dir = new File(System.getProperty("downloadPath"));
        File[] dirContents = dir.listFiles();
        for (File dirContent : dirContents) {
            if (dirContent.getName().equals(fileName)) {
                dirContent.delete();
                return true;
            }
        }
        return false;
    }

    public void logIn(String username, String password) {
        userNameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        saveButton.click();
    }
}
