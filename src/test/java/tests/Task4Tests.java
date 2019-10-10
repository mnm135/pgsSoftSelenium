package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageobjects.Task4Page;

class Task4Tests extends BaseTest {

    private Task4Page task4Page;

    @ParameterizedTest
    @CsvSource({
            "XXX YYY, asd@a.pl, 111-222-333",
            "zxczxc zxczxc, xxx@x.pl, 000-999-888"
    })
    @DisplayName("User can enter correct data in the form")
    void userCanSendCorrectData(String name, String email, String phone) {
        task4Page = new Task4Page(driver);
        task4Page.open();
        task4Page.scrollToElement(task4Page.applyButton);
        task4Page.applyButton.click();
        task4Page.switchToNewWindow();

        task4Page.verifyFormIsDisplayed();

        task4Page.fillAndSaveTheForm(name, phone, email);

        Assertions.assertTrue(task4Page.applicationSentMessage.isDisplayed());
    }

    @ParameterizedTest
    @CsvSource({
            "XXX YYY, asda.pl, 111-222-333",
            "zxczxc zxczxc, xxx@x.pl@wp.pl, 000-999-888"
    })
    @DisplayName("User cant send form with incorrect email")
    void userCantSendApplicationWithIncorrectEmail(String name, String email, String phone) {
        task4Page = new Task4Page(driver);
        task4Page.open();

        task4Page.scrollToElement(task4Page.applyButton);
        task4Page.applyButton.click();
        task4Page.switchToNewWindow();

        task4Page.fillAndSaveTheForm(name, phone, email);

        task4Page.waitForElement(task4Page.wrongEmailError);
        Assertions.assertTrue(task4Page.wrongEmailError.isDisplayed());
    }

    @ParameterizedTest
    @CsvSource({
            "XXX YYY, asd@a.pl, 111-222333",
            "zxczxc zxczxc, xxx@x.pl, 000-999-88899"
    })
    @DisplayName("User cant send form with incorrect phone")
    void userCantSendApplicationWithIncorrectPhone(String name, String email, String phone) {
        task4Page = new Task4Page(driver);
        task4Page.open();

        task4Page.scrollToElement(task4Page.applyButton);
        task4Page.applyButton.click();
        task4Page.switchToNewWindow();

        task4Page.fillAndSaveTheForm(name, phone, email);

        Assertions.assertTrue(task4Page.wrongPhoneError.isDisplayed());
    }

    @ParameterizedTest
    @CsvSource({
            "XXX YYY, asd@, 111222333",
            "zxczxc zxczxc, xxxx.pl, 000-99-888"
    })
    @DisplayName("User cant send form with incorrect email and phone")
    void userCantSendApplicationWithIncorrectEmailAndPhone(String name, String email, String phone) {
        task4Page = new Task4Page(driver);
        task4Page.open();

        task4Page.scrollToElement(task4Page.applyButton);
        task4Page.applyButton.click();
        task4Page.switchToNewWindow();

        task4Page.fillAndSaveTheForm(name, phone, email);

        Assertions.assertTrue(task4Page.wrongEmailError.isDisplayed());
        Assertions.assertTrue(task4Page.wrongPhoneError.isDisplayed());
    }
}
