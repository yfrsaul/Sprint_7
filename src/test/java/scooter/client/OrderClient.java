package scooter.client;

import io.restassured.response.ValidatableResponse;
import pojo.CreateOrderRequest;

import static io.restassured.RestAssured.given;
import static scooter.client.ScooterApi.*;

public class OrderClient {

    public ValidatableResponse create(CreateOrderRequest order) {
        return given()
                .filter(allureFilter())
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(ORDERS)
                .then();
    }

    public ValidatableResponse list() {
        return given()
                .filter(allureFilter())
                .when()
                .get(ORDERS)
                .then();
    }
}

