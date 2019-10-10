package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageobjects.Task3Page;

class Task3Tests extends BaseTest {

    private Task3Page task3Page;

    @Test
    @DisplayName("User is able to enter edit mode")
    void userCanEnableEditMode() {
        task3Page = new Task3Page(driver);
        task3Page.open();

        task3Page.verifyTheFormIsInReadOnlyMode();
        task3Page.enableEditMode();
        task3Page.verifyTheFormIsInEditableMode();
    }

    @ParameterizedTest
    @CsvSource({
            "Adam, Nowak, 1234567890, 123456789",
            "Kamil, XXX, żźćńół asddasd asdq wqqq a, 197213123"
    })
    @DisplayName("User is able to edit the form and save the data")
    void userCanEnterNewDataAndSaveForm(String name, String surname, String notes, String phone) {
        task3Page = new Task3Page(driver);
        task3Page.open();
        task3Page.enableEditMode();

        task3Page.fillTheForm(name, surname, notes, phone);
        task3Page.verifyTheFormIsSavedWithCorrectValues(name, surname, notes, phone);
    }
}