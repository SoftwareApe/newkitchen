package org.softwareape;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Path("/kitchensink")
public class MemberResource {
    private static final Logger log = Logger.getLogger(MemberResource.class.getName());
    
    @Inject
    Template index; // Inject the Qute template

    private static final List<MemberDTO> members = new ArrayList<MemberDTO>(
            Arrays.asList(// Mock data for testing
                    new MemberDTO("Alice", "alice@example.com", "123-456-7890"),
                    new MemberDTO("Bob", "bob@example.com", "987-654-3210")));

    public static List<MemberDTO> getMembers() {
        return members;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getIndex() {
        log.log(Level.INFO, "Rendering Index");
        return index.data("members", members, "errors", Collections.emptyMap()); // Pass the member list to the template
    }

    @POST
    @Path("/members/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response registerMember(@Valid @BeanParam MemberDTO member) {
        log.log(Level.INFO, "Adding member ");

        // Save the new member to the database or in-memory list
        members.addLast(member);

        // Redirect back to the refreshed index page
        return Response.seeOther(URI.create("/kitchensink")).build();
    }
}