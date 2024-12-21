package tests.orders;

import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListGetTest {

    OrderApi orderApi;

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка получения списка заказов без ID курьера")
    public void checkGetOrderListWithoutCourierId() {
        orderApi = new OrderApi();
        ValidatableResponse response = orderApi.getOrderList();

        response.log().all()
                .assertThat()
                .statusCode(HTTP_OK)
                .body(notNullValue());
    }
}
