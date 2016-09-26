package ru.mail.park.main;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

/**
 * Created by kirrok on 26.09.16.
 */
public class Validator {
    @JsonProperty
    private final boolean loginIsOk;
    @JsonProperty
    private final boolean passwordIsOk;
    @JsonProperty
    private final boolean repeatPasswordIsOk;
    @JsonProperty
    private final boolean emailIsOk;

    public static class Builder {
        private boolean loginIsOk;
        private boolean passwordIsOk;
        private boolean emailIsOk;
        private boolean repeatPasswordIsOk;

        public Builder(String login, String password) {
            this.loginIsOk = !StringUtils.isEmpty(login);
            this.passwordIsOk = !StringUtils.isEmpty(password);
        }

        public Builder repeatPassword(String repeatPassword) {
            this.repeatPasswordIsOk = !StringUtils.isEmpty(repeatPassword);
            return this;
        }

        public Builder email(String email) {
            this.emailIsOk = !StringUtils.isEmpty(email);
            return this;
        }

        public Validator build() {
            return new Validator(this);
        }
    }

    private Validator(Builder builder) {
        loginIsOk = builder.loginIsOk;
        passwordIsOk = builder.passwordIsOk;
        repeatPasswordIsOk = builder.repeatPasswordIsOk;
        emailIsOk = builder.emailIsOk;
    }


    public boolean isValid() {
        return loginIsOk && passwordIsOk && repeatPasswordIsOk && emailIsOk ;
    }

    public String StatusAsJson() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.print(mapper.writeValueAsString(this));

            return "";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Convert exception";
        }
    }
}
