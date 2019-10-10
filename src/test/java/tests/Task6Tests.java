package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageobjects.Task6Page;

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
        Assertions.assertTrue(task6Page.logoutButton.isDisplayed());
        Assertions.assertTrue(task6Page.downloadButton.isDisplayed());
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
        Assertions.assertTrue(task6Page.incorrectLoginDataMessage.isDisplayed());
    }

    @ParameterizedTest
    @CsvSource({"tester, 123-xyz, pgs_cv.jpg"
    })
    @DisplayName("User can download the file")
    void userCanDownloadCv(String username, String password, String filename) throws InterruptedException {
        task6Page = new Task6Page(driver);
        task6Page.open();

        task6Page.logIn(username, password);
        task6Page.downloadButton.click();
        Thread.sleep(2000);
        Assertions.assertTrue(task6Page.isFileDownloaded(filename));
    }
}
