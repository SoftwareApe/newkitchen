package org.softwareape;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import java.util.List;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

@Path("/kitchensink")
public class MemberResource {

    @Inject
    Template index; // Inject the Qute template

    private static final List<Member> members = new ArrayList<Member>(
            Arrays.asList(// Mock data for testing
                    new Member("Alice", "alice@example.com", "123-456-7890"),
                    new Member("Bob", "bob@example.com", "987-654-3210")));

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getIndex() {
        return index.data("members", members); // Pass the member list to the template
    }

    @POST
    @Path("/members/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public Response registerMember(@FormParam("name") String name,
            @FormParam("email") String email,
            @FormParam("phoneNumber") String phoneNumber) {
        // Save the new member to the database or in-memory list
        members.addLast(new Member(name, email, phoneNumber));

        // Redirect back to the refreshed index page
        return Response.seeOther(URI.create("/kitchensink")).build();
    }
}