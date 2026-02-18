package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import scooter.client.OrderClient;
import scooter.client.ScooterApi;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrdersListTest {

    private final OrderClient orderClient = new OrderClient();

    @BeforeAll
    static void setUp() {
        ScooterApi.setUp();
    }

    @Test
    @DisplayName("Список заказов,  в ответе есть orders (список)")
    void ordersListReturned() {
        orderClient.list()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", isA(java.util.List.class));
    }
}

