package ru.mail.park.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.mail.park.dataSets.UserDataSet;
import ru.mail.park.repositories.SessionDAO;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by kirrok on 21.10.16.
 */
@Repository
public class SessionDAOImpl implements SessionDAO{
    JdbcTemplate jdbcTemplate;

    @Autowired
    public SessionDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long getUserId(HttpSession session) {
        final String sql = "SELECT user_id FROM session WHERE session_id = ?;";
        return jdbcTemplate.queryForObject(sql, Long.class, session.getId());
    }

    @Override
    public void addUser(HttpSession session, UserDataSet user) {
        final String sql = "INSERT INTO session (session_id, creation_time, last_accessed_time, user_id) VALUES (?, ? ,?, ?);";
        final Date dateCrTime = new Date(session.getCreationTime());
        final Timestamp timestampCreationTime = new Timestamp(dateCrTime.getTime());

        final Date dateAccessedTime = new Date(session.getLastAccessedTime());
        final Timestamp timestampAccessedTime = new Timestamp(dateAccessedTime.getTime());

        jdbcTemplate.update(sql, session.getId(), timestampCreationTime, timestampAccessedTime, user.getId());
    }

    @Override
    public void updateLastAccessedTime(HttpSession session) {
        final String sql = "UPDATE session SET last_accessed_time = ? WHERE session_id = ?";
        final Date date = new Date(session.getLastAccessedTime());
        final Timestamp timestamp = new Timestamp(date.getTime());

        jdbcTemplate.update(sql, timestamp, session.getId());
    }
}
