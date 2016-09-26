package ru.mail.park.model;

import org.springframework.util.StringUtils;

/**
 * Created by Solovyev on 17/09/16.
 */
public class UserProfile {
    private String login;
    private String email;
    private String password;

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
