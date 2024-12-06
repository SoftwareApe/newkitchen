package org.softwareape;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

@QuarkusTest
class MemberResourceTest {
    @BeforeEach
    public void clearDatabase() {
        // Drop Member database before running the tests
        MemberDTO.mongoCollection().drop();
    }

    @Test
    public void testAddingMember() {
        // Step 1: Create a member
        given()
            .formParam("name", "John Doe")
            .formParam("email", "duplicate@example.com")
            .formParam("phoneNumber", "1234567890")
            .when()
            .post("/kitchensink/members/register")
            .then()
            .statusCode(200); // Created
    }

    @Test
    public void testDuplicateEmail() {
        // Step 1: Create a member
        given()
            .formParam("name", "John Doe")
            .formParam("email", "duplicate@example.com")
            .formParam("phoneNumber", "1234567890")
            .when()
            .post("/kitchensink/members/register")
            .then()
            .statusCode(200); // Created

        // Step 2: Attempt to create another member with the same email
        given()
            .formParam("name", "Jane Doe")
            .formParam("email", "duplicate@example.com")
            .formParam("phoneNumber", "0987654321")
            .when()
            .post("/kitchensink/members/register")
            .then()
            .statusCode(400); // Bad Request
    }
}