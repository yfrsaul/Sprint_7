package scooter.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;

public class ScooterApi {
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    public static final String API = "/api/v1";

    public static final String COURIER = API + "/courier";
    public static final String COURIER_LOGIN = API + "/courier/login";
    public static final String ORDERS = API + "/orders";

    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    public static Filter allureFilter() {
        return new AllureRestAssured();
    }
}

