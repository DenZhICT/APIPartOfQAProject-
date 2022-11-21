package guru.qa.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:properties/config.properties")
public interface WebDriverConfig extends Config {

    @Key("baseUrl")
    String getBaseUrl();

    @Key("browserPosition")
    String getBrowserPosition();

    @Key("browserName")
    String getBrowserName();

    @Key("browserVersion")
    String getBrowserVersion();

    @Key("browserSize")
    String getBrowserSize();

    @Key("remoteUrl")
    String getRemoteUrl();

    @Key("baseUri")
    String getBaseUri();
}
