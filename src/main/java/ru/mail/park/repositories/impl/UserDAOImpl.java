package ru.mail.park.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mail.park.dataSets.UserDataSet;
import ru.mail.park.repositories.UserDAO;

import javax.sql.DataSource;

/**
 * Created by kirrok on 21.10.16.
 */

@Repository
public class UserDAOImpl implements UserDAO{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addUser(String login, String password, String email) {
        final String sql = "INSERT INTO user (login, email, password) VALUES(?, ?, ?);";
        jdbcTemplate.update(sql, login, email, password);
    }

    @Override
    public UserDataSet getUserByLogin(String login) {
        final String sql = "SELECT * FROM user u WHERE u.login = ?;";
        return jdbcTemplate.queryForObject(sql,
                (rs, numRow) -> {
                    final UserDataSet tmpUser =
                            new UserDataSet(rs.getString("login"), rs.getString("email"), rs.getString("password"));
                    tmpUser.setMaxScore(rs.getInt("max_score"));
                    tmpUser.setId(rs.getLong("id"));
                    return tmpUser;
                }, login);
    }

    @Override
    public UserDataSet getUserById(long userId) {
        final String sql = "SELECT * FROM user u WHERE u.id = ?;";
        return jdbcTemplate.queryForObject(sql,
                (rs, numRow) -> {
                    final UserDataSet tmpUser =
                            new UserDataSet(rs.getString("login"), rs.getString("email"), rs.getString("password"));
                    tmpUser.setMaxScore(rs.getInt("max_score"));
                    tmpUser.setId(rs.getLong("id"));
                    return tmpUser;
                }, userId);
    }

    public void updateUser(UserDataSet user) {
        final String sql = "UPDATE user SET max_score = ?, email = ?, password = ?, login = ? WHERE id = ?;";
        jdbcTemplate.update(sql, user.getMaxScore(), user.getEmail(), user.getPassword(), user.getPassword());
    }
}
