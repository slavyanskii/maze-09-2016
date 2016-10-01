package ru.mail.park.services;

import org.springframework.stereotype.Service;
import ru.mail.park.model.UserProfile;

import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


@Service
public class SessionService {

    private Map<String, UserProfile> sessionIdToUser = new ConcurrentHashMap<>();

    public void addUser(String sessionId, UserProfile user) {
        sessionIdToUser.put(sessionId, user);
    }

    public UserProfile getUser(String login) {
        return sessionIdToUser.get(login);
    }
}
