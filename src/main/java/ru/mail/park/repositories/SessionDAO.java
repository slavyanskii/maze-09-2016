package ru.mail.park.repositories;

import ru.mail.park.dataSets.UserDataSet;
import javax.servlet.http.HttpSession;

/**
 * Created by viacheslav on 25.10.16.
 */
public interface SessionDAO {
    long getUserId(HttpSession session);

    void addUser(HttpSession session, UserDataSet user);

    void updateLastAccessedTime(HttpSession session);
}
