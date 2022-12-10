package guru.qa.pages;

import guru.qa.data.DemoWebShopData;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class CustomerInfoDwsPage {

    public CustomerInfoDwsPage editAccountInfo(DemoWebShopData newData) {

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
