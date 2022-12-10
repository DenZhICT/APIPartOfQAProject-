package guru.qa.tests.apiwithuitests;

import guru.qa.data.LoginData;
import guru.qa.pages.LoginDwsPage;
import guru.qa.pages.MainDwsPage;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Аккаунт тесты для Demo Web Shop")
@Tag("All")
public class LoginTests extends TestBase {


    @DisplayName("Проверка входа в аккаунт")
    @Tag("Login")
    @Test
    void loginTest() {
        LoginDwsPage loginPage = new LoginDwsPage();
        MainDwsPage mainPage = new MainDwsPage();

        LoginData data = ConfigFactory.create(LoginData.class, System.getProperties());

        String cookie = loginPage.login(data.getEmail(), data.getPassword());
        mainPage.checkLogin(data.getEmail(), cookie);
    }
}
