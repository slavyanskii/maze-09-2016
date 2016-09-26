package ru.mail.park.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody RegistrationRequest body,
                                HttpSession httpSession) {
        final String sessionId = httpSession.getId();

        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();
        final String repeatPassword = body.getRepeatPassword();

        final Validator validator = new Validator.Builder(login, password).email(email).repeatPassword(repeatPassword).build();

        if (!validator.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validator.StatusAsJson());
        }
        if (StringUtils.isEmpty(login)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }
        final UserProfile existingUser = accountService.getUser(login);
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        accountService.addUser(login, password, email);
        return ResponseEntity.ok(new SuccessResponse(login));
    }

    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody AuthorizationRequest body) {

        final String login = body.getLogin();
        final String password = body.getPassword();
        final Validator validator = new Validator.Builder(login, password).build();

        if (!validator.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validator.StatusAsJson());
        }

        final UserProfile user = accountService.getUser(login);

        if (user.getPassword().equals(password)) {
            return ResponseEntity.ok(new SuccessResponse(user.getLogin()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"User already exists\"}");
    }

    private static final class AuthorizationRequest {
        private String login;
        private String password;

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
        private String repeatPassword;
        private String email;


        private RegistrationRequest(String login, String password, String email, String repeatPassword) {
            this.login = login;
            this.password = password;
            this.email = email;
            this.repeatPassword = repeatPassword;

        }

        public String getRepeatPassword() {
            return repeatPassword;
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
