package guru.qa.pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class MainDwsPage {
    private void dropCookie(String getCookie) {
        String logAUTH = "NOPCOMMERCE.AUTH";

        step("Подкладка Cookie", () -> {
            open("/Themes/DefaultClean/Content/images/logo.png");
            Cookie authCookie = new Cookie(logAUTH, getCookie);
            WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        });
    }

    public void checkLogin(String email, String getCookie) {
        dropCookie(getCookie);

        step("Открываем главную страницу", () ->
                open(""));

        step("Проверяет совпадение почты с " + email, () ->
                $(".account").shouldHave(text(email)));
    }
}
