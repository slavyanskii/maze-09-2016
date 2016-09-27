package ru.mail.park.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

/**
 * Created by kirrok on 26.09.16.
 */
public final class Validator {
    @JsonProperty
    private final boolean loginValid;
    @JsonProperty
    private final boolean passwordValid;
    @JsonProperty
    private boolean emailValid = true;

    public Validator(String login, String password) {
        this.loginValid = !StringUtils.isEmpty(login);
        this.passwordValid = !StringUtils.isEmpty(password);
    }

    public Validator(String login, String password, String email) {
        this.loginValid = !StringUtils.isEmpty(login);
        this.passwordValid = !StringUtils.isEmpty(password);
        this.emailValid = !StringUtils.isEmpty(email);
    }

    public boolean isValid() {
        return loginValid && passwordValid && emailValid;
    }

    public String validationStatusAsJson() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
