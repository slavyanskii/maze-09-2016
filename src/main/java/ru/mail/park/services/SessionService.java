package ru.mail.park.services;

import org.springframework.stereotype.Service;
import ru.mail.park.model.UserProfile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * ДЗ: реализовать класс, который по соответствующему id сессии будет отдавать пользователей(т.е. реализовать авторизацию по сессии)
 */
@Service
public class SessionService {

    private Map<String, UserProfile> sessionIdToUser = new HashMap<>();

    public void addUser(String sessionId, UserProfile user) {
        sessionIdToUser.put(sessionId, user);
    }

    public UserProfile getUser(String login) {
        return sessionIdToUser.get(login);
    }
}
