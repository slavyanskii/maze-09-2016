package ru.mail.park.services.impl;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.park.Application;
import ru.mail.park.dataSets.UserDataSet;
import ru.mail.park.repositories.UserDAO;
import ru.mail.park.services.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    UserDAO userDao;

    @Autowired
    public AccountServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(String login, String password, String email) {
        userDao.addUser(login, password, email);
    }

    @Nullable
    @Override
    public UserDataSet getUser(String login) {
        try {
            return userDao.getUserByLogin(login);
        } catch (EmptyResultDataAccessException e) {
            Application.logger.warn(e);
            return null;
        }
    }

    @Override
    public void updateUser(UserDataSet user) {
        userDao.updateUser(user);
    }
}