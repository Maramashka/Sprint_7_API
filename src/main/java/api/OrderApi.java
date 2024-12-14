package api;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.OrderCreate;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi{

    public static final String ORDER_POST_CREATE = "/api/v1/orders";
    public static final String ORDER_GET_LIST = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(OrderCreate order) {
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(ORDER_POST_CREATE)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .spec(requestSpecification())
                .get(ORDER_GET_LIST)
                .then();
    }
}
