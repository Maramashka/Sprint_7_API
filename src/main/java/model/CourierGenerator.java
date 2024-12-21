package model;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    @Step("Generate random model with 3 parameters")
    public static Courier getRandomCourier(String loginParam, String passwordParam,
                                           String firstNameParam) {
        String login = loginParam + RandomStringUtils.randomAlphabetic(5);
        String password = passwordParam + RandomStringUtils.randomAlphabetic(5);
        String firstName = firstNameParam + RandomStringUtils.randomAlphabetic(5);

        return new Courier(login, password, firstName);
    }

    @Step("Generate random model with 2 parameters")
    public static Courier getRandomCourier2param(String loginParam, String passwordParam) {
        String login = loginParam + RandomStringUtils.randomAlphabetic(5);
        String password = passwordParam + RandomStringUtils.randomAlphabetic(5);

        return new Courier(login, password);
    }
}

