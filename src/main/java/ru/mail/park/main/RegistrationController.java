package ru.mail.park.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.model.UserProfile;
import ru.mail.park.services.AccountService;
import ru.mail.park.services.SessionService;
import ru.mail.park.exceptions.*;
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

    @RequestMapping(path = "/api/registration", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity login(@RequestBody RegistrationRequest body) {
        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();
        final Validator validator = new Validator(login, password, email);

        if (!validator.isValid()) {
            throw new CastomException(HttpStatus.BAD_REQUEST,validator.validationStatusAsJson()); //наш эксепшн который мы можем крутить как хотим
        }
        final UserProfile existingUser = accountService.getUser(login);
        if (existingUser != null) {
            throw new CastomException(HttpStatus.CONFLICT,"User already exists.");
        }

        accountService.addUser(login, password, email);
        return ResponseEntity.ok(new SuccessResponse(login));
    }

    @RequestMapping(path = "/api/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody AuthorizationRequest body,
                               HttpSession httpSession) {

        final String login = body.getLogin();
        final String password = body.getPassword();
        final Validator validator = new Validator(login, password);

        if (!validator.isValid()) {
            throw new CastomException(HttpStatus.BAD_REQUEST, validator.validationStatusAsJson());
        }

        final UserProfile user = accountService.getUser(login);

        if (user != null && user.getPassword().equals(password)) {
            sessionService.addUser(httpSession.getId(), user);
            return ResponseEntity.ok(new SuccessResponse(user.getLogin()));
        }
        throw new CastomException(HttpStatus.UNAUTHORIZED, "Wrong login or password.");
    }

    private static final class AuthorizationRequest {
        private String login;
        private String password;

        @SuppressWarnings("unused")
        private AuthorizationRequest() {
        }

        @SuppressWarnings("unused")
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

        @SuppressWarnings("unused")
        private RegistrationRequest() {
        }

        @SuppressWarnings("unused")
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
