package ru.mail.park.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.exceptions.ErrorResponse;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.AccountService;
import ru.mail.park.services.SessionService;

import javax.servlet.http.HttpSession;

/**
 * Created by Solovyev on 06/09/16.
 */

@RestController
public class RegistrationController {
    private final AccountService accountService;
    private final SessionService sessionService;

    @Autowired
    public RegistrationController(AccountService accountService,
                                  SessionService sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @RequestMapping(path = "/api/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody RegistrationRequest body) {

        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),"Invalid input"));
        }

        final UserProfile existingUser = accountService.getUser(login);

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.toString(),"User already exist"));
        }

        accountService.addUser(login, password, email);
        return ResponseEntity.ok(new SuccessResponse(login));
    }

    @RequestMapping(path = "/api/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody AuthorizationRequest body,
                               HttpSession httpSession) {

        final String login = body.getLogin();
        final String password = body.getPassword();

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),"Invalid input"));
        }

        final UserProfile user = accountService.getUser(login);

        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.toString(), "Wrong login or password"));

        }
        sessionService.addUser(httpSession.getId(), user);
        return ResponseEntity.ok(new SuccessResponse(user.getLogin()));
    }

    private static final class AuthorizationRequest {
        private String login;
        private String password;

        private AuthorizationRequest() {
        }

        private AuthorizationRequest(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    private static final class RegistrationRequest {
        private String login;
        private String password;
        private String email;

        private RegistrationRequest() {
        }

        private RegistrationRequest(String login, String password, String email) {
            this.login = login;
            this.password = password;
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getEmail() {
            return email;
        }
    }

    private static final class SuccessResponse {
        private String login;

        private SuccessResponse(String login) {
            this.login = login;
        }

        @SuppressWarnings("unused")
        public String getLogin() {
            return login;
        }
    }
}
