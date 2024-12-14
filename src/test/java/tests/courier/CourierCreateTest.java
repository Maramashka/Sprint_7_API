package tests.courier;

import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static model.CourierGenerator.getRandomCourier;
import static model.CourierGenerator.getRandomCourier2param;
import static org.hamcrest.CoreMatchers.is;

public class CourierCreateTest {

    Courier courier;
    CourierApi courierApi;
    Integer courierId;

    String login = "Koza";
    String password = "Koza";
    String firstName = "Koza";
    String emptyString = "";

    @Before
    public void setUp() {
        courierApi = new CourierApi();
    }

    @After
    public void tearDown() {
        ValidatableResponse loginResponse = courierApi.loginCourier(courier);
        courierId = loginResponse.extract().path("id");

        if (courierId != null) {
            courierApi.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Create courier")
    @Description("Создание курьера c тремя параметрами")
    public void checkCreated() {

        courier = getRandomCourier(login, password, firstName);

        ValidatableResponse response = courierApi.createCourier(courier);

        response.log().all()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Create courier with 2 Required fields")
    @Description("Создание курьера с двумя обязательными параметрами")
    public void checkCreatedWith2RequiredFields() {

        courier = getRandomCourier2param(login, password);

        ValidatableResponse response = courierApi.createCourier(courier);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body("ok", is(true));

    }


    @Test
    @DisplayName("Two required couriers cannot created")
    @Description("Невозможно создать двух одинаковых курьеров")
    public void check2RequiredCouriersCannotCreated() {

        courier = new Courier(login, password, firstName);
        courierApi.createCourier(courier);

        Courier courierDataCopy = new Courier(login, password, firstName);

        ValidatableResponse response = courierApi.createCourier(courierDataCopy);
        response.log().all()
                .statusCode(HTTP_CONFLICT)
                .and()
                .assertThat()
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }


    @Test
    @DisplayName("Two couriers with identical login cannot be created")
    @Description("Невозможно создать курьера с уже существующим логином")
    public void check2IdenticalLoginCannotCreated() {
        courier = new Courier(login, password, firstName);
        courierApi.createCourier(courier);

        Courier courier2 = new Courier(login,
                RandomStringUtils.randomAlphanumeric(5), RandomStringUtils.randomAlphanumeric(5));

        ValidatableResponse response = courierApi.createCourier(courier2);
        response.log().all()
                .statusCode(HTTP_CONFLICT)
                .and()
                .assertThat()
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Courier cannot created without login")
    @Description("Невозможно создать курьера без логина")
    public void checkCannotCreatedWithoutLogin() {

        courier = new Courier(null, password, firstName);

        ValidatableResponse response = courierApi.createCourier(courier);

        response.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Courier with empty line login cannot be created ")
    @Description("Логин - пустая строка, невозможно создать курьера")
    public void checkCannotCreatedWithEmptyLineLogin() {

        courier = new Courier(emptyString, password, firstName);

        ValidatableResponse response = courierApi.createCourier(courier);

        response.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Courier without password cannot be created")
    @Description("Невозможно создать курьера без пароля")
    public void checkCannotCreatedWithoutPassword() {

        courier = new Courier(login, null, firstName);

        ValidatableResponse response = courierApi.createCourier(courier);

        response.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Courier with empty line password cannot be created")
    @Description("Пароль - пустая строка, невозможно создать курьера")
    public void checkCannotCreatedWithEmptyLinePassword() {

        courier = new Courier(login, emptyString, firstName);

        ValidatableResponse response = courierApi.createCourier(courier);

        response.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}