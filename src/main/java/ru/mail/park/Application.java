package ru.mail.park;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.mail.park.main.Validator;

/**
 * Created by Solovyev on 06/09/16.
 */
@SpringBootApplication
public class Application {
    public static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
