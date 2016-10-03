package ru.mail.park.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Solovyev on 17/09/16.
 */
public class UserProfile {
    private String login;
    private String email;
    private String password;
    private Long id;
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.id = ID_GENERATOR.getAndIncrement();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }
}
