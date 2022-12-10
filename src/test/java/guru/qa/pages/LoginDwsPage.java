package guru.qa.pages;

import guru.qa.helpers.CustomApiListener;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class LoginDwsPage {
    String getCookie;

    public String login(String login, String password) {
        String logAUTH = "NOPCOMMERCE.AUTH";

        step("Получение Cookie пользователя c почтой " + login, () -> {
            getCookie =
                    given()
                            .filter(CustomApiListener.withCustomTemplates())
                            .formParam("Email", login)
                            .formParam("Password", password)
                            .when()
                            .post("/login")
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie(logAUTH);
        });

        return getCookie;
    }
}
