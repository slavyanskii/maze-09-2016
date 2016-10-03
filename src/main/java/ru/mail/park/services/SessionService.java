package ru.mail.park.services;

import org.springframework.stereotype.Service;
import ru.mail.park.model.UserProfile;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;


@Service
public class SessionService {

    private Map<String, UserProfile> sessionIdToUser = new ConcurrentHashMap<>();

    public void addUser(String sessionId, UserProfile user) {
        sessionIdToUser.put(sessionId, user);
    }

    @SuppressWarnings("unused")
    public UserProfile getUserByLogin(String login) {
        return sessionIdToUser.get(login);
    }

    @SuppressWarnings("unused")
    public UserProfile getUserBySession(String sessionId) {
        return sessionIdToUser.get(sessionId);
    }
}
