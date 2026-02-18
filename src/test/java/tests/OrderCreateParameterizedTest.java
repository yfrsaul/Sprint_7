package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import scooter.client.OrderClient;
import scooter.client.ScooterApi;
import pojo.CreateOrderRequest;
import pojo.CreateOrderResponse;
import util.TestData;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OrderCreateParameterizedTest {

    private final OrderClient orderClient = new OrderClient();

    @BeforeAll
    static void setUp() {
        ScooterApi.setUp();
    }

    static Stream<List<String>> colors() {
        return Stream.of(
                List.of("BLACK"),
                List.of("GREY"),
                List.of("BLACK", "GREY"),
                null
        );
    }

    @ParameterizedTest(name = "Создание заказа с цветами: {0}")
    @MethodSource("colors")
    @DisplayName("Создание заказа при любых вариантах цветов в ответе видим track")
    void createOrderWithColors(List<String> colors) {
        CreateOrderRequest order = TestData.baseOrder(colors);

        CreateOrderResponse resp = orderClient.create(order)
                .statusCode(201)
                .extract().as(CreateOrderResponse.class);

        assertNotNull(resp.getTrack());
    }
}

