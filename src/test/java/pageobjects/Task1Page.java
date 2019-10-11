package pageobjects;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Task1Page extends BasePage {

    private static final String URL = "https://testingcup.pgs-soft.com/task_1";
    private static final String ADD_BUTTON_TEMPLAE = "//form//button[@data-product-name='%s']";
    private static final String INPUT_TEMPLATE = "//form//button[@data-product-name='%s']/parent::span/following-sibling::input";
    private static final String QUANTITY_TEMPLATE = "//span[@data-quantity-for='%s']";
    private static final String PRICE_TEMPLATE = "//div/h4[text()='%s']/following-sibling::p[1]";
    private static final String ALLERT_MESSAGE = "Łączna ilość produktów w koszyku nie może przekroczyć 100.";

    @FindBy(className = "summary-quantity")
    public WebElement totalNumberOfProductsInBasket;
    @FindBy(className = "summary-price")
    public WebElement totalPrice;

    public Task1Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }

    public String getFormattedTotalPrice() {
        return totalPrice.getText().split(" ")[0];
    }

    public WebElement getAddButtonByName(String name) {
        return driver.findElement(By.xpath(String.format(ADD_BUTTON_TEMPLAE, name)));
    }

    public WebElement getInputByName(String name) {
        return driver.findElement(By.xpath(String.format(INPUT_TEMPLATE, name)));
    }

    public WebElement getQuantityOfItemInBasketByName(String name) {
        return driver.findElement(By.xpath(String.format(QUANTITY_TEMPLATE, name)));
    }

    public String getPriceByName(String name) {
        WebElement priceElement = driver.findElement(By.xpath(String.format(PRICE_TEMPLATE, name)));
        String price = priceElement.getText().split(" ")[1];
        return price;
    }

    public String getExpectedProductPriceByNameAndAmount(String name, String amount) {
        String price = getPriceByName(name);
        double sum = Double.valueOf(price) * Integer.valueOf(amount);
        return formatPriceValues(sum);
    }

    private String formatPriceValues(double value) {
        double sum = Math.round(value * 100d) / 100d;
        return String.format(Locale.ROOT,"%.2f", sum);
    }

    @Step("Add item to basket")
    public void addItemToBasketByNameAndAmount(String name, String amount) {
        getInputByName(name).clear();
        getInputByName(name).sendKeys(amount);
        scrollToElement(getAddButtonByName(name));
        getAddButtonByName(name).click();
    }

    public void addItemsToBasketFromTheList(List<String> names, List<String> amounts) {
        Iterator namesIterator = names.iterator();
        Iterator amountsIterator = amounts.iterator();
        while(namesIterator.hasNext() && amountsIterator.hasNext()) {
            addItemToBasketByNameAndAmount(namesIterator.next().toString(), amountsIterator.next().toString());
        }
    }

    @Step("Verify that quantity of each item was correctly calculated in basket")
    public void verifyQuantityOfItemsInBasketFromList(List<String> names, List<String> amounts) {
        Iterator namesIterator = names.iterator();
        Iterator amountsIterator = amounts.iterator();
        while(namesIterator.hasNext() && amountsIterator.hasNext()) {
            Assertions.assertEquals(getQuantityOfItemInBasketByName(namesIterator.next().toString()).getText(), amountsIterator.next().toString());
        }
    }

    @Step("Verify that total price in basket was calculated correctly")
    public void verifyTotalPriceInBasketFromList(List<String> names, List<String> amounts) {
        double sum = 0;
        Iterator namesIterator = names.iterator();
        Iterator amountsIterator = amounts.iterator();
        while(namesIterator.hasNext() && amountsIterator.hasNext()) {
            sum += Double.valueOf(getExpectedProductPriceByNameAndAmount(namesIterator.next().toString(), amountsIterator.next().toString()));
        }
        Assertions.assertEquals(formatPriceValues(sum), getFormattedTotalPrice());
    }

    public String getTotalNumberOfProductsInBasket() {
        return totalNumberOfProductsInBasket.getText();
    }

    public String getExpectedTotalItemsInBasket(List<String> amounts) {
        return String.valueOf(amounts.stream().mapToInt(Integer::valueOf).sum());
    }

    @Step("Verify that alert is displayed")
    public void verifyAlertMessageIsDisplayed() {
        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals(alert.getText(), ALLERT_MESSAGE);
        alert.accept();
    }


}
