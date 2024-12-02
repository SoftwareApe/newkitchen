package org.softwareape;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MemberResource {

    // TODO: In-memory store for members (replace with database logic in production)
    private static final List<Member> members = new ArrayList<>();

    // Endpoint to register a new member
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerMember(@FormParam("name") String name,
                                   @FormParam("email") String email,
                                   @FormParam("phoneNumber") String phoneNumber) {
        Member newMember = new Member(name, email, phoneNumber);
        members.add(newMember);
        return Response.ok("Member registered successfully!").build();
    }

    // Endpoint to fetch all members
    @GET
    public List<Member> getAllMembers() {
        return members;
    }
}