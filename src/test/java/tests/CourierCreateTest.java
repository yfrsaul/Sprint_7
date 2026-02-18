package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;
import scooter.client.CourierClient;
import scooter.client.ScooterApi;
import pojo.*;
import util.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class CourierCreateTest {

    private final CourierClient courierClient = new CourierClient();
    private Integer courierIdToDelete;

    @BeforeAll
    static void setUp() {
        ScooterApi.setUp();
    }

    @AfterEach
    void tearDown() {
        if (courierIdToDelete != null) {
            OkResponse del = courierClient.delete(courierIdToDelete)
                    .statusCode(200)
                    .extract().as(OkResponse.class);
            assertTrue(del.isOk());
            courierIdToDelete = null;
        }
    }

    @Test
    @DisplayName("Курьера можно создать, код ответа 201 и ok true")
    void courierCanBeCreated() {
        Courier courier = TestData.randomCourier();

        OkResponse create = courierClient.create(courier)
                .statusCode(201)
                .extract().as(OkResponse.class);
        assertTrue(create.isOk());

        LoginResponse login = courierClient.login(CourierCredentials.from(courier))
                .statusCode(200)
                .extract().as(LoginResponse.class);
        assertNotNull(login.getId());
        courierIdToDelete = login.getId();
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров, статус код 409 и 'Этот логин уже используется'")
    void cannotCreateDuplicateCourier() {
        Courier courier = TestData.randomCourier();

        OkResponse create1 = courierClient.create(courier)
                .statusCode(201)
                .extract().as(OkResponse.class);
        assertTrue(create1.isOk());

        MessageResponse err = courierClient.create(courier)
                .statusCode(409)
                .extract().as(MessageResponse.class);
        assertEquals("Этот логин уже используется", err.getMessage());

        courierIdToDelete = courierClient.login(CourierCredentials.from(courier))
                .statusCode(200)
                .extract().as(LoginResponse.class)
                .getId();
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина, статус код 400 и 'Недостаточно данных для создания учетной записи'")
    void cannotCreateCourierWithoutLogin() {
        Courier courier = TestData.randomCourier();
        courier.setLogin(null);

        MessageResponse err = courierClient.create(courier)
                .statusCode(400)
                .extract().as(MessageResponse.class);
        assertEquals("Недостаточно данных для создания учетной записи", err.getMessage());
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля, статус код 400 и 'Недостаточно данных для создания учетной записи'")
    void cannotCreateCourierWithoutPassword() {
        Courier courier = TestData.randomCourier();
        courier.setPassword(null);

        MessageResponse err = courierClient.create(courier)
                .statusCode(400)
                .extract().as(MessageResponse.class);
        assertEquals("Недостаточно данных для создания учетной записи", err.getMessage());
    }
}

