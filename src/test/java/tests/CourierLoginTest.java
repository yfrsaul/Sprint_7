package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;
import scooter.client.CourierClient;
import scooter.client.ScooterApi;
import pojo.*;
import util.TestData;

import static org.junit.jupiter.api.Assertions.*;

public class CourierLoginTest {

    private final CourierClient courierClient = new CourierClient();

    private Courier courier;
    private Integer courierIdToDelete;

    @BeforeAll
    static void setUp() {
        ScooterApi.setUp();
    }

    @BeforeEach
    void createCourier() {
        courier = TestData.randomCourier();
        courierClient.create(courier).statusCode(201);
        courierIdToDelete = null;
    }

    @AfterEach
    void tearDown() {
        if (courierIdToDelete == null) {
            courierIdToDelete = courierClient.login(CourierCredentials.from(courier))
                    .statusCode(200)
                    .extract().as(LoginResponse.class)
                    .getId();
        }

        OkResponse del = courierClient.delete(courierIdToDelete)
                .statusCode(200)
                .extract().as(OkResponse.class);
        assertTrue(del.isOk());
    }

    @Test
    @DisplayName("Курьер может залогиниться, статус код 200 и id")
    void courierCanLogin() {
        LoginResponse login = courierClient.login(CourierCredentials.from(courier))
                .statusCode(200)
                .extract().as(LoginResponse.class);

        assertNotNull(login.getId());
        courierIdToDelete = login.getId();
    }

    @Test
    @DisplayName("Без логина, статус код 400 и 'Недостаточно данных для входа'")
    void loginFailsWithoutLogin() {
        CourierCredentials creds = new CourierCredentials(null, courier.getPassword());

        MessageResponse err = courierClient.login(creds)
                .statusCode(400)
                .extract().as(MessageResponse.class);

        assertEquals("Недостаточно данных для входа", err.getMessage());
    }

    @Test
    @DisplayName("Без пароля, статус код 400 и 'Недостаточно данных для входа'")
    void loginFailsWithoutPassword() {
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), null);

        MessageResponse err = courierClient.login(creds)
                .statusCode(400)
                .extract().as(MessageResponse.class);

        assertEquals("Недостаточно данных для входа", err.getMessage());
    }

    @Test
    @DisplayName("Неверные логин,пароль, статус код 404 и 'Учетная запись не найдена'")
    void loginFailsWithWrongPassword() {
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), "wrong");

        MessageResponse err = courierClient.login(creds)
                .statusCode(404)
                .extract().as(MessageResponse.class);

        assertEquals("Учетная запись не найдена", err.getMessage());
    }

    @Test
    @DisplayName("Несуществующий пользователь, статус код 404 и 'Учетная запись не найдена'")
    void loginFailsForNonExistingCourier() {
        CourierCredentials creds = new CourierCredentials("no_such_login_" + java.util.UUID.randomUUID(), "1234");

        MessageResponse err = courierClient.login(creds)
                .statusCode(404)
                .extract().as(MessageResponse.class);

        assertEquals("Учетная запись не найдена", err.getMessage());
    }
}

