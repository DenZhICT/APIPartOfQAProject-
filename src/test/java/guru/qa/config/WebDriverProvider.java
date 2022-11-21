package guru.qa.config;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;

public class WebDriverProvider {

    public void setWebDriverConfiguration() {

        WebDriverConfig config = ConfigFactory.create(WebDriverConfig.class, System.getProperties());

        Configuration.baseUrl = config.getBaseUrl();
        RestAssured.baseURI = config.getBaseUri();
        Configuration.browserPosition = config.getBrowserPosition();

        Configuration.browser = System.getProperty("browser_name", config.getBrowserName());
        Configuration.browserVersion = System.getProperty("browser_version", config.getBrowserVersion());
        Configuration.browserSize = System.getProperty("browser_size", config.getBrowserSize());

        String env = System.getProperty("environment");
        if (env != null && env.equals("remote")) {
            Configuration.remote = config.getRemoteUrl();
        }
    }
}
