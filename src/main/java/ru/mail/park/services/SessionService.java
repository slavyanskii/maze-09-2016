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

    private Map<String, UserProfile> userNameToUser = new HashMap<>();

    public UserProfile addUser(String login, String password, String email) {
        final UserProfile userProfile = new UserProfile(login, email, password);
        userNameToUser.put(login, userProfile);
        return userProfile;
    }
    public UserProfile getUser(String login) {
        return userNameToUser.get(login);
    }
    //To be done
}
