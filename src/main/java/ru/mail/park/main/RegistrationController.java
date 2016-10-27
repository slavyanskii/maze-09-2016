package ru.mail.park.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mail.park.exceptions.ErrorResponse;
import ru.mail.park.dataSets.UserDataSet;
import ru.mail.park.services.impl.AccountServiceImpl;
import ru.mail.park.services.impl.SessionServiceImpl;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Solovyev on 06/09/16.
 */

@RestController
public class RegistrationController {
    private final AccountServiceImpl accountService;
    private final SessionServiceImpl sessionService;

    @Autowired
    public RegistrationController(AccountServiceImpl accountService,
                                  SessionServiceImpl sessionService) {
        this.accountService = accountService;
        this.sessionService = sessionService;
    }

    @RequestMapping(path = "/session", method = RequestMethod.GET)
    public ResponseEntity sessionCheck(HttpSession session) {

        final UserDataSet user = sessionService.getUser(session);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.NOT_LOGGED_IN_MSG));
        }
        return ResponseEntity.ok(new SuccessResponse(user.getLogin()));
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody @Valid RegistrationRequest body) {

        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();

        final UserDataSet existingUser = accountService.getUser(login);

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse(HttpStatus.FORBIDDEN.toString(), ErrorResponse.USER_ALREADY_EXISTS_MSG));
        }
        accountService.addUser(login, password, email);
        return ResponseEntity.ok(new SuccessResponse(login));
    }

    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody @Valid AuthorizationRequest body,
                               HttpSession session) {

        final String login = body.getLogin();
        final String password = body.getPassword();

        final UserDataSet user = accountService.getUser(login);

        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.AUTHORIZATION_ERROR_MSG));
        }
        sessionService.addUser(session, user);
        return ResponseEntity.ok(new SuccessResponse(user.getLogin()));
    }

    private static final class AuthorizationRequest {
        @NotNull
        private String login;
        @NotNull
        private String password;

        private AuthorizationRequest() {
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }

    private static final class RegistrationRequest {
        @NotNull
        private String login;
        @NotNull
        private String password;
        @NotNull
        private String email;

        private RegistrationRequest() {
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

        public String getLogin() {
            return login;
        }
    }
}
