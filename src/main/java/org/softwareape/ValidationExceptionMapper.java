package org.softwareape;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.inject.Inject;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger log = Logger.getLogger(ValidationExceptionMapper.class.getName());

    @Inject
    Template index; // Inject the Qute template

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            // For some reason we get the whole path here e.g. registerMember.member.phoneNumber
            String[] fieldSplit = field.split("\\.");
            field = fieldSplit[fieldSplit.length - 1];
            errors.put(field, violation.getMessage());
            log.log(Level.WARNING, "Getting a validation error here for: " + field);
            log.warning(errors.get(field));
        }

        // Render the Qute template with error messages
        TemplateInstance instance = index.data("members", MemberResource.getMembers(), "errors", errors);
        log.warning(instance.render());

        return Response.status(Response.Status.BAD_REQUEST)
                       .type(MediaType.TEXT_HTML)
                       .entity(instance.render())
                       .build();
    }
}
