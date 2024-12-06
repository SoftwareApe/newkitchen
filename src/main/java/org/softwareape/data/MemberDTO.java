package org.softwareape.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.FormParam;

import java.util.UUID;

import org.bson.codecs.pojo.annotations.BsonId;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity
public class MemberDTO extends PanacheMongoEntityBase {
    @BsonId
    public String id;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    @FormParam("name")
    public String name;

    @NotNull
    @NotEmpty
    @Email
    @FormParam("email")
    public String email;

    @NotNull
    @FormParam("phoneNumber")
    @Size(min = 10, max = 12)
    @Digits(fraction = 0, integer = 12)
    public String phoneNumber;

    // Default constructor (necessary for form submission)
    public MemberDTO() {
        this.id = UUID.randomUUID().toString(); // Automatically generate a UUID
    }

    // Default constructor
    public MemberDTO(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID().toString(); // Automatically generate a UUID
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}