package org.softwareape;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

import org.hibernate.validator.internal.engine.path.PathImpl;

import java.util.Collections;
import java.util.HashSet;

public class ValidationUtils {

    public static void throwEmailAlreadyExistsViolation(String emailField) {
        // Create a ConstraintViolation for the "email" field
        ConstraintViolation<Object> violation = new ConstraintViolation<>() {
            @Override
            public String getMessage() {
                return "Email already exists";
            }

            @Override
            public String getMessageTemplate() {
                return "{email.already.exists}";
            }

            @Override
            public Object getRootBean() {
                return null;
            }

            @Override
            public Class<Object> getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null; // You could optionally include the conflicting value here
            }

            @Override
            public Path getPropertyPath() {
                return PathImpl.createPathFromString(emailField);
            }

            @Override
            public Object[] getExecutableParameters() {
                return null; // For method-level validation, return the method parameters
            }

            @Override
            public Object getExecutableReturnValue() {
                return null; // For method-level validation, return the return value
            }

            @Override
            public <U> U unwrap(Class<U> type) {
                if (type.isInstance(this)) {
                    return type.cast(this);
                }
                throw new IllegalArgumentException("Type " + type + " is not supported");
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                throw new UnsupportedOperationException("Unimplemented method 'getConstraintDescriptor'");
            }
        };

        // Throw ConstraintViolationException with the single violation
        throw new ConstraintViolationException(new HashSet<>(Collections.singleton(violation)));
    }
}
