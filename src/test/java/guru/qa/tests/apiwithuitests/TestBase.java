package guru.qa.tests.apiwithuitests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import guru.qa.config.WebDriverProvider;
import guru.qa.helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static io.qameta.allure.Allure.step;

public class TestBase {

    @BeforeAll
    static void configure() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        new WebDriverProvider().setWebDriverConfiguration();
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();

        String browser = System.getProperty("browser_name", "chrome");
        if (browser.equals("chrome")) {
            Attach.browserConsoleLogs(); // log'и не берутся из firefox
        }

        String env = System.getProperty("environment");
        if (env != null && env.equals("remote")) {
            Attach.addVideo();
        }

        step("Закрыти браузера", Selenide::closeWebDriver);
    }
}
