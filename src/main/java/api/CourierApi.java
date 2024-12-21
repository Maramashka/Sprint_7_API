package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import model.CourierLogin;

import static io.restassured.RestAssured.given;
import static model.CourierLogin.from;

public class CourierApi extends RestApi {

    public static final String COURIER_POST_CREATE = "/api/v1/courier";
    public static final String COURIER_POST_LOGIN = "/api/v1/courier/login";
    public static final String COURIER_DELETE = "/api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(COURIER_POST_CREATE)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse loginCourier(Courier courier) {

        CourierLogin courierLogin = from(courier);

        return given()
                .spec(requestSpecification())
                .and()
                .body(courierLogin)
                .when()
                .post(COURIER_POST_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        return given()
                .spec(requestSpecification())
                .and()
                .when()
                .delete(COURIER_DELETE + courierId)
                .then().log().all();
    }
}
