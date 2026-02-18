package scooter.client;

import io.restassured.response.ValidatableResponse;
import pojo.Courier;
import pojo.CourierCredentials;

import static io.restassured.RestAssured.given;
import static scooter.client.ScooterApi.*;

public class CourierClient {

    public ValidatableResponse create(Courier courier) {
        return given()
                .filter(allureFilter())
                .header("Content-Type", "application/json")
                .body(courier)
                .when()
                .post(COURIER)
                .then();
    }

    public ValidatableResponse login(CourierCredentials creds) {
        return given()
                .filter(allureFilter())
                .header("Content-Type", "application/json")
                .body(creds)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    public ValidatableResponse delete(int courierId) {
        return given()
                .filter(allureFilter())
                .when()
                .delete(COURIER + "/" + courierId)
                .then();
    }
}

