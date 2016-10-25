package ru.mail.park.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mail.park.dataSets.UserDataSet;

import javax.sql.DataSource;

/**
 * Created by kirrok on 21.10.16.
 */

@SuppressWarnings("all")
@Repository
public class UserDAO {
    JdbcTemplate jdbcTemplate;
    DataSource dataSource;

    @Autowired
    public UserDAO(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addUser(String login, String password, String email) {
        final String SQL = "INSERT INTO user (login, email, password) VALUES(?, ?, ?);";
        jdbcTemplate.update(SQL, login, email, password);
    }

    public UserDataSet getUserByLogin(String login) {
        final String SQL = "SELECT * FROM user u WHERE u.login = ?;";
        return jdbcTemplate.queryForObject(SQL,
                (rs, numRow) -> {
                    final UserDataSet tmpUser =
                            new UserDataSet(rs.getString("login"), rs.getString("email"), rs.getString("password"));
                    tmpUser.setMaxScore(rs.getInt("max_score"));
                    tmpUser.setId(rs.getLong("id"));
                    return tmpUser;
                }, login);
    }

    public UserDataSet getUserById(long userId) {
        final String SQL = "SELECT * FROM user u WHERE u.id = ?;";
        return jdbcTemplate.queryForObject(SQL,
                (rs, numRow) -> {
                    final UserDataSet tmpUser =
                            new UserDataSet(rs.getString("login"), rs.getString("email"), rs.getString("password"));
                    tmpUser.setMaxScore(rs.getInt("max_score"));
                    tmpUser.setId(rs.getLong("id"));
                    return tmpUser;
                }, userId);
    }

    public void updateUser(UserDataSet user) {
        final String SQL = "UPDATE user SET max_score = ?, email = ?, password = ?, login = ? WHERE id = ?;";
        jdbcTemplate.update(SQL, user.getMaxScore(), user.getEmail(), user.getPassword(), user.getPassword());
    }
}
