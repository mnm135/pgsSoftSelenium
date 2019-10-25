package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageobjects.Task6Page;

import static io.qameta.allure.Allure.step;

class Task6Tests extends BaseTest {

    private Task6Page task6Page;

    @ParameterizedTest
    @CsvSource({"tester, 123-xyz"
    })
    @DisplayName("user can login with correct data")
    void userCanLoginWithCorrectData(String username, String password) {
        task6Page = new Task6Page(driver);
        task6Page.open();
        task6Page.logIn(username, password);

        step("Verify that download and logout buttons are displayed", (step) -> {
            Assertions.assertTrue(task6Page.logoutButton.isDisplayed());
            Assertions.assertTrue(task6Page.downloadButton.isDisplayed());
        });
    }

    @ParameterizedTest
    @CsvSource({
            "test, 123-xyz",
            "123-xyz, tester",
            "tester, password"
    })
    @DisplayName("User cant login with incorrect data")
    void userCantLoginWithIncorrectData(String username, String password) {
        task6Page = new Task6Page(driver);
        task6Page.open();

        task6Page.logIn(username, password);
        step("Verify that error regarding incorrect data is displyed", (step) -> {
            Assertions.assertTrue(task6Page.incorrectLoginDataMessage.isDisplayed());
        });
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({"tester, 123-xyz, pgs_cv.jpg"
    })
    @DisplayName("User can download the file")
    void userCanDownloadCv(String username, String password, String filename) {
        task6Page = new Task6Page(driver);
        task6Page.open();
        task6Page.logIn(username, password);

        step("Click download button", (step) -> {
            task6Page.downloadButton.click();
        });
        step("Verify that file is correctly downloaded", (step) -> {
            //@TODO change thread.sleep
            Thread.sleep(2000);
            Assertions.assertTrue(task6Page.isFileDownloaded(filename));
        });
    }
}
