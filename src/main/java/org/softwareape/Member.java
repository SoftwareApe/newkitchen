package org.softwareape;

import java.util.UUID;

// @SuppressWarnings("serial")
// @Entity
// @XmlRootElement
// @Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Member { // implements Serializable {

    // @Id
    // @GeneratedValue
    private String id;

    // @NotNull
    // @Size(min = 1, max = 25)
    // @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String name;

    // @NotNull
    // @NotEmpty
    // @Email
    private String email;

    // @NotNull
    // @Size(min = 10, max = 12)
    // @Digits(fraction = 0, integer = 12)
    // @Column(name = "phone_number")
    private String phoneNumber;

    // Default constructor (required by JPA)
    public Member(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}