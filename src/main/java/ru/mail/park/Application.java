package ru.mail.park;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mail.park.main.Validator;

/**
 * Created by Solovyev on 06/09/16.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        final Validator validator = new Validator.Builder("", "").email("email").repeatPassword("dwdw").build();
        System.out.println(validator.StatusAsJson());

//        SpringApplication.run(Application.class, args);

    }
}
