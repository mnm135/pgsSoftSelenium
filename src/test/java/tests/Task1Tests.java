package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pageobjects.Task1Page;

import java.util.List;

class Task1Tests extends BaseTest {

    private Task1Page task1Page;

    @ParameterizedTest
    @CsvSource({
            "Piłka, 3",
            "Okulary, 5"
    })
    @DisplayName("Total quantity and price in the cart is calculated correctly for one type items")
    void quantityAndPriceIsCorrectForOneItemType(String name, String amount) {
        task1Page = new Task1Page(driver);
        task1Page.open();

        task1Page.addItemToBasketByNameAndAmount(name, amount);

        Assertions.assertEquals(task1Page.getQuantityOfItemInBasketByName(name).getText(), amount);
        Assertions.assertEquals(task1Page.getPriceByNameAndAmount(name, amount),  task1Page.getFormattedTotalPrice());
    }

    @ParameterizedTest
    @CsvSource({
            "Piłka, 3, Okulary, 5, Kabel, 15, Monitor, 25, Kostka, 11"
    })
    @DisplayName("Total quantity and price in the cart is calculated correctly for various type items")
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
        task1Page.verifyPricesOfItemsInBasketFromList(names, amounts);
        //@TODO verify sum of all items in basket

    }
}
