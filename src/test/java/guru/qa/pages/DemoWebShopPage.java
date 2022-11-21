package guru.qa.pages;

import com.codeborne.selenide.WebDriverRunner;
import guru.qa.data.DemoWebShopData;
import guru.qa.helpers.CustomApiListener;
import io.restassured.response.Response;
import org.openqa.selenium.Cookie;

import java.util.concurrent.atomic.AtomicReference;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


public class DemoWebShopPage {

    private final String logAUTH = "NOPCOMMERCE.AUTH";
    private final String tokenName = "__RequestVerificationToken";
    String getCookie;

    private Response getRegister() {
        AtomicReference<Response> response = new AtomicReference<>();

        step("Достаём Cookie и Token", () ->
        response.set(given()
                .filter(CustomApiListener.withCustomTemplates())
                .when()
                .get("/register")
                .then()
                .statusCode(200)
                .extract()
                .response()));

        return response.get();
    }

    private void dropCookie() {
        step("Подкладка Cookie", () -> {
            open("/Themes/DefaultClean/Content/images/logo.png");
            Cookie authCookie = new Cookie(logAUTH, getCookie);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });
    }

    public DemoWebShopPage registrationApi(DemoWebShopData data) {
        Response register = getRegister();
        String token = register.htmlPath().getString("**.find{it.@name == '__RequestVerificationToken'}.@value");
        String cookie = register.cookie(tokenName);

        step("Регистрация нового пользователя с почтой "+data.email, () -> {
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

    public DemoWebShopPage login(String login, String password) {
        step("Получение Cookie пользователя c почтой "+login, () -> {
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

        return this;
    }

    public void checkLogin(String email) {
        dropCookie();

        step("Открываем главную страницу", () ->
                open(""));

        step("Проверяет совпадение почты с "+email, () ->
            $(".account").shouldHave(text(email)));
    }

    public DemoWebShopPage editAccountInfo(DemoWebShopData newData) {

        step("Открываем главную страницу", () ->
                open("/customer/info"));

        step("Простановка новых данных", () -> {
            $(".fieldset").find(byText(newData.gender)).click();
            $("#FirstName").setValue(newData.firstName);
            $("#LastName").setValue(newData.lastName);
            $("#Email").setValue(newData.email);
            $(".save-customer-info-button").click();
        });
        return this;
    }
    public void checkAccountInfo(DemoWebShopData newData) {
        refresh();
        step("Проверка изменения данных", () -> {
            $("#FirstName").shouldHave(value(newData.firstName));
            $("#LastName").shouldHave(value(newData.lastName));
            $(".account").shouldHave(text(newData.email));
        });
    }
}
