package org.softwareape;

import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.FormParam;

// @SuppressWarnings("serial")
// @Entity
// @XmlRootElement
// @Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class MemberDTO { // implements Serializable {

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
    }

    // Default constructor
    public MemberDTO(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}