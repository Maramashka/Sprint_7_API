package tests.courier;

import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierLogin;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static model.CourierGenerator.getRandomCourier;
import static model.CourierGenerator.getRandomCourier2param;
import static model.CourierLogin.from;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class CourierLoginTest {
    Courier courier;
    CourierLogin courierLogin;
    CourierApi courierApi;
    Integer courierId;
    ValidatableResponse response;

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

        courierId = response.extract().path("id");
        if (courierId != null) {
            courierApi.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Successful authorization")
    @Description("Авторизация курьера с 3 полями")
    public void checkLogin() {

        courier = getRandomCourier(login, password, firstName);
        courierApi.createCourier(courier);

        response = courierApi.loginCourier(courier);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body(containsString("id"));
    }

    @Test
    @DisplayName("Successful authorization with two required fields")
    @Description("Авторизация курьера с двумя обязательными полями")
    public void checkLoginWith2RequiredFields() {

        courier = getRandomCourier2param(login, password);
        courierApi.createCourier(courier);
        courierLogin = from(courier);

        response = courierApi.loginCourier(courier);

        response.log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .and()
                .body(containsString("id"));
    }

    @Test
    @DisplayName("Failed authorization with incorrect login")
    @Description("Ошибка авторизации при некорректно заполненном логине")
    public void checkFailedAuthorizationWithIncorrectLogin() {

        courier = getRandomCourier(login, password, firstName);
        courierApi.createCourier(courier);

        courier.setLogin(RandomStringUtils.randomAlphabetic(5));

        response = courierApi.loginCourier(courier);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .and()
                .body("message", is("Учетная запись не найдена"));

        courierId = response.extract().path("id");
    }

    @Test
    @DisplayName("Failed authorization with incorrect password")
    @Description("Ошибка авторизации при некорректно заполненном пароле")
    public void checkFailedAuthorizationWithIncorrectPassword() {

        courier = getRandomCourier(login, password, firstName);
        courierApi.createCourier(courier);

        courier.setPassword(RandomStringUtils.randomAlphabetic(5));

        response = courierApi.loginCourier(courier);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .and()
                .body("message", is("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Failed authorization with empty login&password")
    @Description("Ошибка авторизации при незаполненных полях (пустые строки)")
    public void checkFailedAuthorizationWithEmptyLoginAndPassword() {

        courier = getRandomCourier(login, password, firstName);
        courierApi.createCourier(courier);

        courier.setLogin(emptyString);
        courier.setPassword(emptyString);

        response = courierApi.loginCourier(courier);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Failed authorization with empty login")
    @Description("Ошибка авторизации при незаполненном логине (пустая строка)")
    public void checkFailedAuthorizationWithEmptyLogin() {

        courier = getRandomCourier(login, password, firstName);
        courierApi.createCourier(courier);
        courier.setLogin(emptyString);

        response = courierApi.loginCourier(courier);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Failed authorization with empty password")
    @Description("Ошибка авторизации при незаполненном пароле (пустая строка)")
    public void checkFailedAuthorizationWithEmptyPassword() {

        courier = getRandomCourier(login, password, firstName);
        courierApi.createCourier(courier);
        courier.setPassword(emptyString);

        response = courierApi.loginCourier(courier);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Failed authorization with login&password = null")
    @Description("Ошибка авторизации при незаполненных полях (null)")
    public void checkFailedAuthorizationWithNullLoginAndPassword() {

        courier = getRandomCourier(login, password, firstName);
        courierApi.createCourier(courier);
        courierLogin = from(courier);

        courier.setLogin(null);
        courier.setPassword(null);

        response = courierApi.loginCourier(courier);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для входа"));

    }
}


