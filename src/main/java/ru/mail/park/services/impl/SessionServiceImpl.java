package ru.mail.park.services.impl;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.Application;
import ru.mail.park.dataSets.UserDataSet;
import ru.mail.park.repositories.impl.SessionDAOImpl;
import ru.mail.park.repositories.impl.UserDAOImpl;
import ru.mail.park.services.SessionService;

import javax.servlet.http.HttpSession;


@Service
@Transactional
public class SessionServiceImpl implements SessionService {
    SessionDAOImpl sessionDAOImpl;
    UserDAOImpl userDAOImpl;

    @Autowired
    public SessionServiceImpl(SessionDAOImpl sessionDAOImpl, UserDAOImpl userDAOImpl) {
        this.sessionDAOImpl = sessionDAOImpl;
        this.userDAOImpl = userDAOImpl;
    }

    @Override
    public void addUser(HttpSession session, UserDataSet user) {
        try {
            sessionDAOImpl.addUser(session, user);
        } catch (DuplicateKeyException e) {
            Application.logger.warn(e);
            sessionDAOImpl.updateLastAccessedTime(session);
        }
    }

    @Nullable
    @Override
    public UserDataSet getUser(HttpSession session) {
        final long userId;
        try {
            userId = sessionDAOImpl.getUserId(session);
        } catch (EmptyResultDataAccessException e) {
            Application.logger.warn(e);
            return null;
        }
        return userDAOImpl.getUserById(userId);
    }
}
