package scooter.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.Courier;
import pojo.CourierCredentials;

import static io.restassured.RestAssured.given;
import static scooter.client.ScooterApi.*;

public class CourierClient {

    @Step("Создать курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .filter(allureFilter())
                .header("Content-Type", "application/json")
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    @Step("Логин курьера")
    public ValidatableResponse login(CourierCredentials creds) {
        return given()
                .filter(allureFilter())
                .header("Content-Type", "application/json")
                .body(creds)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Удалить курьера")
    public ValidatableResponse delete(int courierId) {
        return given()
                .filter(allureFilter())
                .when()
                .delete(COURIER + "/" + courierId)
                .then();
    }
}

