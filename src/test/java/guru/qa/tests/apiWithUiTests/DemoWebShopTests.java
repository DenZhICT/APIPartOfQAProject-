package guru.qa.tests.apiWithUiTests;


import guru.qa.base.TestBase;
import guru.qa.data.DemoWebShopData;
import guru.qa.data.LoginData;
import guru.qa.pages.DemoWebShopPage;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@DisplayName("Аккаунт тесты для Demo Web Shop")
@Tag("All")
public class DemoWebShopTests extends TestBase {

    DemoWebShopPage page = new DemoWebShopPage();

    @DisplayName("Проверка входа в аккаунт")
    @Tag("Login")
    @Test
    void loginTest() {

        LoginData data = ConfigFactory.create(LoginData.class, System.getProperties());

        page.login(data.getEmail(), data.getPassword())
                .checkLogin(data.getEmail());
    }

    @DisplayName("Проверка регистрации нового аккаунта")
    @Tag("Registration")
    @Test
    void registrationTest() {

        DemoWebShopData data = new DemoWebShopData();

        page.registrationApi(data)
                .login(data.email, data.password)
                .checkLogin(data.email);
    }

    @DisplayName("Проверка измения информации аккаунта")
    @Tag("ChangeInfo")
    @Test
    void accountChangeInfoTest() {
        DemoWebShopData data = new DemoWebShopData();

        page.registrationApi(data)
                .login(data.email, data.password)
                .checkLogin(data.email);

        data = new DemoWebShopData();

        page.editAccountInfo(data)
                .checkAccountInfo(data);
    }
}
