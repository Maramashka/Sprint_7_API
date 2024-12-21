package tests.orders;

import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderCreate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.containsString;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedTest {

    List<String> colour;
    OrderApi orderApi;
    OrderCreate orderCreate;

    public OrderCreateParameterizedTest(List<String> colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {List.of("BLACK", "GREY")},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of()}
        };
    }

    @Before
    public void setUp() {
        orderApi = new OrderApi();
        orderCreate = new OrderCreate(colour);
    }

    @Test
    @DisplayName("Create order with variable colour")
    @Description("Создание заказа с самокатами разных цветов")
    public void checkCreateOrderWithVariableColour() {
        ValidatableResponse response = orderApi.createOrder(orderCreate);
        response.log().all()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .body(containsString("track"));
    }
}
