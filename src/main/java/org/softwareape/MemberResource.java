package org.softwareape;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import java.util.List;

@Path("/kitchensink")
public class MemberResource {

    @Inject
    Template index; // Inject the Qute template

    private static final List<Member> members = List.of( // Mock data for testing
        new Member("Alice", "alice@example.com", "123-456-7890"),
        new Member("Bob", "bob@example.com", "987-654-3210")
    );

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getIndex() {
        return index.data("members", members).render(); // Pass the member list to the template
    }

    @POST
    @Path("/members/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void registerMember(@FormParam("name") String name,
                               @FormParam("email") String email,
                               @FormParam("phoneNumber") String phoneNumber) {
        // Save the new member to the database or in-memory list
        members.add(new Member(name, email, phoneNumber));
    }
}