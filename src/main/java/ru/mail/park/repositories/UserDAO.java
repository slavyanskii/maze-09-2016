package ru.mail.park.repositories;

import ru.mail.park.dataSets.UserDataSet;

/**
 * Created by viacheslav on 25.10.16.
 */
public interface UserDAO {
    void addUser(String login, String password, String email);

    UserDataSet getUserByLogin(String login);

    UserDataSet getUserById(long userId);

    void updateUser(UserDataSet user);
}
