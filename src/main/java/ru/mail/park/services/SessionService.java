package ru.mail.park.services;

import ru.mail.park.dataSets.UserDataSet;

import javax.servlet.http.HttpSession;

/**
 * Created by kirrok on 21.10.16.
 */
public interface SessionService {

    public void addUser(HttpSession session, UserDataSet user);

    public UserDataSet getUser(HttpSession session);

    public void delUser(HttpSession session);
}
