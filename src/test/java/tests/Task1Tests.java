package tests;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageobjects.Task1Page;
import static io.qameta.allure.Allure.step;


import java.util.List;

@Story("Task 1 tests - shop basket")
class Task1Tests extends BaseTest {

    private Task1Page task1Page;

    @ParameterizedTest(name = "Total quantity and price in the cart is calculated correctly for one type items")
    @CsvSource({
            "Piłka, 3",
            "Okulary, 5"
    })
    void quantityAndPriceIsCorrectForOneItemType(String name, String amount) {
        task1Page = new Task1Page(driver);
        task1Page.open();

        task1Page.addItemToBasketByNameAndAmount(name, amount);

        Assertions.assertEquals(task1Page.getQuantityOfItemInBasketByName(name).getText(), amount);
        Assertions.assertEquals(task1Page.getExpectedProductPriceByNameAndAmount(name, amount), task1Page.getFormattedTotalPrice());
    }

    @ParameterizedTest(name = "Total quantity and price in the cart is calculated correctly for various type items")
    @CsvSource({
            "Piłka, 3, Okulary, 5, Kabel, 15, Monitor, 25, Kostka, 11",
            "Aparat, 10, Kamera, 33, Poduszka, 1, Zeszyt, 19, Słuchawki, 2"
    })
    void quantityAndPriceIsCorrectForDifferentTypesOfProducts(String name1, String amount1,
                                                              String name2, String amount2,
                                                              String name3, String amount3,
                                                              String name4, String amount4,
                                                              String name5, String amount5) {
        task1Page = new Task1Page(driver);
        task1Page.open();

        List<String> names = List.of(name1, name2, name3, name4, name5);
        List<String> amounts = List.of(amount1, amount2, amount3, amount4, amount5);

        task1Page.addItemsToBasketFromTheList(names, amounts);
        task1Page.verifyQuantityOfItemsInBasketFromList(names, amounts);
        task1Page.verifyTotalPriceInBasketFromList(names, amounts);

        Assertions.assertEquals(task1Page.getTotalNumberOfProductsInBasket(), task1Page.getExpectedTotalItemsInBasket(amounts));
    }

    @ParameterizedTest(name = "User can't add more than 100 items to the basket")
    @CsvSource({
            "Piłka, 3, Okulary, 5, Kabel, 15, Monitor, 25, Kostka, 70",
            "Aparat, 10, Kamera, 10, Poduszka, 10, Zeszyt, 70, Słuchawki, 1"
    })
    void totalAmountInBasketCantBeMoreThanOneHundred(String name1, String amount1,
                                                     String name2, String amount2,
                                                     String name3, String amount3,
                                                     String name4, String amount4,
                                                     String supernumeraryName, String supernumeraryAmount) {
        task1Page = new Task1Page(driver);
        task1Page.open();

        List<String> names = List.of(name1, name2, name3, name4);
        List<String> amounts = List.of(amount1, amount2, amount3, amount4);

        task1Page.addItemsToBasketFromTheList(names, amounts);

        task1Page.addItemToBasketByNameAndAmount(supernumeraryName, supernumeraryAmount);
        task1Page.verifyAlertMessageIsDisplayed();

        step("Verify that supernumerary item was not added to basket", (step) -> {
            task1Page.verifyQuantityOfItemsInBasketFromList(names, amounts);
            task1Page.verifyTotalPriceInBasketFromList(names, amounts);
            Assertions.assertEquals(task1Page.getTotalNumberOfProductsInBasket(), task1Page.getExpectedTotalItemsInBasket(amounts));
        });
    }
}
