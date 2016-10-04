package ru.mail.park.java.tackle;

import org.junit.Test;
import ru.mail.park.main.Validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kirrok on 04.10.16.
 */
public class ValidatorTest {

    final String validName = "kirill";
    final String validPassword = "12345678";
    final String validEmail = "some@email.ru";

    @Test
    public void testValidateValidDate() {
        final Validator validValidator = new Validator(validName, validEmail, validPassword);
        assertTrue(validValidator.isValid());

        final Validator badValidatorOne = new Validator("", validPassword, validEmail);
        assertFalse(badValidatorOne.isValid());

        final Validator badValidatorTwo = new Validator(validName, "", validPassword);
        assertFalse(badValidatorTwo.isValid());

        final Validator badValidatorThree = new Validator(validName, validPassword, "");
        assertFalse(badValidatorThree.isValid());
    }
}
