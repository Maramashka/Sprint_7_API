package tests.courier;

import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static model.CourierGenerator.getRandomCourier;
import static org.hamcrest.CoreMatchers.is;

public class CourierDeleteTest {

    Courier courier;
    CourierApi courierApi;
    int courierId;

    String login = "Koza";
    String password = "Koza";
    String firstName = "Koza";

    @Before
    public void setUp() {

        courierApi = new CourierApi();
        courier = getRandomCourier(login, password, firstName);
        courierApi.createCourier(courier);

        ValidatableResponse response = courierApi.loginCourier(courier);
        courierId = response.extract().path("id");
    }

    @After
    public void tearDown() {
        courierApi.deleteCourier(courierId);
    }

    @DisplayName("Delete courier")
    @Description("Проверка удаления курьера")
    @Test
    public void checkDeleteCourier() {
        ValidatableResponse response = courierApi.deleteCourier(courierId);

        if (courierId != 0) {
            response.log().all()
                    .assertThat()
                    .statusCode(HTTP_OK)
                    .body("ok", is(true));
        } else {
            response.log().all()
                    .assertThat()
                    .statusCode(HTTP_NOT_FOUND)
                    .body("message", is("Курьера с таким id нет"));
        }
    }
}