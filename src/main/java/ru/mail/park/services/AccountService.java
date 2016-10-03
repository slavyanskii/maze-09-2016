package ru.mail.park.services;

import org.springframework.stereotype.Service;
import ru.mail.park.model.UserProfile;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class AccountService {
    private Map<String, UserProfile> userNameToUser = new ConcurrentHashMap<>();

    public UserProfile addUser(String login, String password, String email) {
        final UserProfile userProfile = new UserProfile(login, email, password);
        userNameToUser.put(login, userProfile);
        return userProfile;
    }
    public UserProfile getUser(String login) {
        return userNameToUser.get(login);
    }
}
