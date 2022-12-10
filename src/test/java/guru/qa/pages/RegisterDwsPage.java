package guru.qa.pages;

import guru.qa.data.DemoWebShopData;
import guru.qa.helpers.CustomApiListener;
import io.restassured.response.Response;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class RegisterDwsPage {

    private Response getRegister() {
        return
                step("Достаём Cookie и Token", () ->
                        given()
                                .filter(CustomApiListener.withCustomTemplates())
                                .when()
                                .get("/register")
                                .then()
                                .statusCode(200)
                                .extract()
                                .response());
    }

    public RegisterDwsPage registrationApi(DemoWebShopData data) {
        String tokenName = "__RequestVerificationToken";

        Response register = getRegister();
        String token = register.htmlPath().getString("**.find{it.@name == '__RequestVerificationToken'}.@value");
        String cookie = register.cookie(tokenName);

        step("Регистрация нового пользователя с почтой " + data.email, () -> {
            given()
                    .filter(CustomApiListener.withCustomTemplates())
                    .formParam("Gender", data.gender.charAt(0))
                    .formParam("FirstName", data.firstName)
                    .formParam("LastName", data.lastName)
                    .formParam("Email", data.email)
                    .formParam("Password", data.password)
                    .formParam("ConfirmPassword", data.password)
                    .formParam(tokenName, token)
                    .cookie(tokenName, cookie)
                    .when()
                    .post("/register")
                    .then()
                    .statusCode(302);
        });

        return this;
    }
}
