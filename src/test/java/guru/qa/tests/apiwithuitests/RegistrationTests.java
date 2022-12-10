package guru.qa.tests.apiwithuitests;

import guru.qa.data.DemoWebShopData;
import guru.qa.pages.LoginDwsPage;
import guru.qa.pages.MainDwsPage;
import guru.qa.pages.RegisterDwsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Аккаунт тесты для Demo Web Shop")
@Tag("All")
public class RegistrationTests extends TestBase {

    @DisplayName("Проверка регистрации нового аккаунта")
    @Tag("Registration")
    @Test
    void registrationTest() {
        RegisterDwsPage registerPage = new RegisterDwsPage();
        LoginDwsPage loginPage = new LoginDwsPage();
        MainDwsPage mainPage = new MainDwsPage();

        DemoWebShopData data = new DemoWebShopData();

        registerPage.registrationApi(data);
        String cookie = loginPage.login(data.email, data.password);
        mainPage.checkLogin(data.email, cookie);
    }
}
