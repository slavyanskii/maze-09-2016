package ru.mail.park.services;

import ru.mail.park.dataSets.UserDataSet;

/**
 * Created by kirrok on 21.10.16.
 */
public interface AccountService {
    void addUser(String login, String password, String email);

    UserDataSet getUser(String email);

    void updateUser(UserDataSet user);
}
