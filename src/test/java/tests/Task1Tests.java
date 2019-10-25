package tests;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
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

        step("Verify that items were correctly added to basket", (step) -> {
            Assertions.assertEquals(task1Page.getQuantityOfItemInBasketByName(name).getText(), amount);
            Assertions.assertEquals(task1Page.getExpectedProductPriceByNameAndAmount(name, amount), task1Page.getFormattedTotalPrice());
        });
    }

    @ParameterizedTest(name = "Total quantity and price in the cart is calculated correctly for various type items")
    @MethodSource("testdata.ShoppingCartItemsProvider#cartItemsData")
    void quantityAndPriceIsCorrectForDifferentTypesOfProducts(List<String> names, List<String> amounts) {
        task1Page = new Task1Page(driver);
        task1Page.open();

        task1Page.addItemsToBasketFromTheList(names, amounts);
        task1Page.verifyQuantityOfItemsInBasketFromList(names, amounts);
        task1Page.verifyTotalPriceInBasketFromList(names, amounts);

        step("Verify that total number of products in basket was calculated correctly", (step) -> {
            Assertions.assertEquals(task1Page.getTotalNumberOfProductsInBasket(), task1Page.getExpectedTotalItemsInBasket(amounts));
        });
    }

    @ParameterizedTest(name = "User can't add more than 100 items to the basket")
    @MethodSource("testdata.ShoppingCartItemsProvider#cartItemsNegativeData")
    void totalAmountInBasketCantBeMoreThanOneHundred(List<String> names, List<String> amounts,
                                                     String supernumeraryName, String supernumeraryAmount) {
        task1Page = new Task1Page(driver);
        task1Page.open();

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
