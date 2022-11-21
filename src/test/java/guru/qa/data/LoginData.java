package guru.qa.data;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:properties/login.properties")
public interface LoginData extends Config {

    @Key("email")
    String getEmail();

    @Key("password")
    String getPassword();
}
