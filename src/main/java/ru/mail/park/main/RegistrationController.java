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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @RequestMapping(path = "/session", method = RequestMethod.GET)
    public ResponseEntity sessionCheck(HttpSession httpSession){
        final UserProfile user = accountService.getUser(httpSession.getId());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorResponse(HttpStatus.BAD_GATEWAY.toString(),"Not logged in"));
        }
        return ResponseEntity.ok(new SuccessResponse(user.getLogin()));
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody @Valid RegistrationRequest body) {

        final String login = body.getLogin();
        final String password = body.getPassword();
        final String email = body.getEmail();

        final UserProfile existingUser = accountService.getUser(login);

        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.toString(),ErrorResponse.USER_ALREADY_EXISTS));
        }

        accountService.addUser(login, password, email);
        return ResponseEntity.ok(new SuccessResponse(login));
    }

    @RequestMapping(path = "/auth", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody @Valid AuthorizationRequest body,
                               HttpSession httpSession) {

        final String login = body.getLogin();
        final String password = body.getPassword();

        final UserProfile user = accountService.getUser(login);

        if (user == null || !user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ErrorResponse.AUTHORIZATION_ERROR));
        }
        sessionService.addUser(httpSession.getId(), user);
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

        @SuppressWarnings("unused")
        public String getLogin() {
            return login;
        }
    }
}
