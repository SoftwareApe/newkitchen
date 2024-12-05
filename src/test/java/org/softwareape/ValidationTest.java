package org.softwareape;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;

import java.util.Set;
import java.util.logging.Logger;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ValidationTest {
    private static final Logger log = Logger.getLogger(MemberResource.class.getName());
    @Test
    void testNameValidation() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        MemberDTO member = new MemberDTO();
        member.name = "namewithnumb3rs"; // Invalid: Blank name
        member.email = "invalid-email"; // Invalid: Bad email format
        member.phoneNumber = "123456"; // Valid: Assume no regex applied

        Set<ConstraintViolation<MemberDTO>> violations = validator.validate(member);

        violations.forEach(violation -> {
            String field = violation.getPropertyPath().toString(); 
            String message = violation.getMessage();
            if (field.equals("name")) {
                log.warning("Found name violation " + message);
                assertEquals("Must not contain numbers", message);
            }
            else if (field.equals("email")){
                log.warning("Found email violation " + message);
                assertEquals("must be a well-formed email address", message);
            }
            else if (field.equals("phoneNumber")) {
                log.warning("Found phoneNumber violation " + message);
                assertEquals("size must be between 10 and 12", message);
            }
            else {
                // Should never get here
                assertEquals("", field);
            }
        });

        assertEquals(3, violations.size());
    }
}