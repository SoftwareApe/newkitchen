package org.softwareape;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.validation.Valid;

import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.net.URI;
import java.util.Collections;

@Path("/kitchensink")
public class MemberResource {
    private static final Logger log = Logger.getLogger(MemberResource.class.getName());
    
    @Inject
    Template index; // Inject the Qute template

    public static List<MemberDTO> getMembers() {
        return MemberDTO.listAll();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getIndex() {
        log.log(Level.INFO, "Rendering Index");
        return index.data("members", getMembers(), "errors", Collections.emptyMap());
    }

    @POST
    @Path("/members/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response registerMember(@Valid @BeanParam MemberDTO member) {
        log.log(Level.INFO, "Adding member ");

        // Save the new member to the database, but only if the email doesn't exist yet
        if (MemberDTO.find("email", member.email).firstResultOptional().isEmpty()) {
            member.persist(); // Save to MongoDB
        }
        else {
            ValidationUtils.throwEmailAlreadyExistsViolation("email");
        }

        // Redirect back to the refreshed index page
        return Response.seeOther(URI.create("/kitchensink")).build();
    }
}