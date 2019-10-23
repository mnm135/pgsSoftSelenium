package testdata;

import org.junit.jupiter.params.provider.Arguments;
import static java.util.Arrays.asList;

import java.util.stream.Stream;

public class ShoppingCartItemsProvider {

    static Stream<Arguments> cartItemsData() {
        return Stream.of(
                Arguments.of(asList("Piłka", "Okulary", "Kabel", "Kostka"), asList("12", "2", "3", "14")),
                Arguments.of(asList("Słuchawki", "Poduszka", "Kamera"), asList("7", "8", "9")));
    }

    static Stream<Arguments> cartItemsNegativeData() {
        return Stream.of(
                Arguments.of(asList("Piłka", "Okulary", "Kabel", "Kostka"), asList("50", "10", "39", "1"),"Poduszka", "1"),
                Arguments.of(asList("Słuchawki", "Poduszka", "Kamera"), asList("70", "8", "8"), "Okulary", "20"));
    }
}
