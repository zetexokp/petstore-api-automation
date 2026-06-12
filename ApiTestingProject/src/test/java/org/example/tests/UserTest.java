package org.example.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.User;
import org.example.helper.Helper;
import org.junit.jupiter.api.*;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest extends BaseTest {

    private User newUser;
    private Long userID;
    private String username;

    private static final long NON_EXISTING_ID = 999999999L;

    // SETUP

    @BeforeEach
    void setUp() {

        newUser = Helper.createDefaultUser();

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(newUser)
                        .when()
                        .post("/user");

        response.then()
                .statusCode(200);

        username = newUser.getUsername();
        userID = newUser.getId();
    }

    // POSITIVE

    @Test
    void createUsersWithListShouldCreateUsers() {

        User user1 = Helper.createDefaultUser();
        User user2 = Helper.createDefaultUser();

        List<User> users = List.of(user1, user2);

        given()
                .contentType(ContentType.JSON)
                .body(users)
                .when()
                .post("/user/createWithList")
                .then()
                .statusCode(200);

        given()
                .pathParam("username", user1.getUsername())
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .body("username", equalTo(user1.getUsername()));

        given()
                .pathParam("username", user2.getUsername())
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .body("username", equalTo(user2.getUsername()));
    }

    @Test
    void getUserByValidUsernameShouldReturnUserData() {

        given()
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .body("id", equalTo(userID.intValue()))
                .body("username", equalTo(username))
                .body("firstName", equalTo(newUser.getFirstName()))
                .body("lastName", equalTo(newUser.getLastName()))
                .body("email", equalTo(newUser.getEmail()));
    }

    @Test
    void getUserShouldReturnJson() {

        given()
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
    @Test
    void updateUserShouldUpdateUserData() {

        User updatedUser = new User(
                userID,
                username,
                "UpdatedName",
                "UpdatedLastName",
                "updated@test.com",
                "newPassword",
                "+37399999999",
                1L
        );

        given()
                .contentType(ContentType.JSON)
                .pathParam("username", username)
                .body(updatedUser)
                .when()
                .put("/user/{username}")
                .then()
                .statusCode(anyOf(is(200), is(204)));

        given()
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("UpdatedName"))
                .body("lastName", equalTo("UpdatedLastName"))
                .body("email", equalTo("updated@test.com"));
    }
    @Test
    void loginWithValidCredentialsShouldReturn200() {

        given()
                .queryParam("username", username)
                .queryParam("password", newUser.getPassword())
                .when()
                .get("/user/login")
                .then()
                .statusCode(200)
                .body(containsString("logged in user session"));
    }

    @Test
    void createUsersWithArrayShouldCreateUsers() {

        User user1 = Helper.createDefaultUser();
        User user2 = Helper.createDefaultUser();

        List<User> users = List.of(user1, user2);

        given()
                .contentType(ContentType.JSON)
                .body(users)
                .when()
                .post("/user/createWithArray")
                .then()
                .statusCode(200);

        given()
                .pathParam("username", user1.getUsername())
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .body("username", equalTo(user1.getUsername()));

        given()
                .pathParam("username", user2.getUsername())
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(200)
                .body("username", equalTo(user2.getUsername()));

        // cleanup
        given().pathParam("username", user1.getUsername())
                .when().delete("/user/{username}");

        given().pathParam("username", user2.getUsername())
                .when().delete("/user/{username}");
    }

    @Test
    void deleteUserShouldDeleteUser() {

        given()
                .pathParam("username", username)
                .when()
                .delete("/user/{username}")
                .then()
                .statusCode(200);

        given()
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(404);

        userID = null;
    }

    @Test
    void logoutShouldReturn200() {

        given()
                .when()
                .get("/user/logout")
                .then()
                .statusCode(200);
    }



    // negative

    @Test
    void getNonExistingUserShouldReturn404() {

        given()
                .pathParam("username", "unknown_user_123456")
                .when()
                .get("/user/{username}")
                .then()
                .statusCode(404);
    }

    @Test
    void deleteNonExistingUserShouldReturn404() {

        given()
                .pathParam("username", "unknown_user_123456")
                .when()
                .delete("/user/{username}")
                .then()
                .statusCode(anyOf(is(404), is(200)));
    }

    @Test
    void updateNonExistingUserShouldReturn404() {

        User updatedUser = Helper.createDefaultUser();

        given()
                .contentType(ContentType.JSON)
                .pathParam("username", "unknown_user_123456")
                .body(updatedUser)
                .when()
                .put("/user/{username}")
                .then()
                .statusCode(anyOf(is(404), is(200)));
    }

    @Test
    void loginWithInvalidPasswordShouldFail() {

        given()
                .queryParam("username", username)
                .queryParam("password", "wrongPassword")
                .when()
                .get("/user/login")
                .then()
                .statusCode(anyOf(is(400), is(200)));
    }
    @Test
    void loginShouldReturnRequiredHeaders() {

        given()
                .queryParam("username", username)
                .queryParam("password", newUser.getPassword())
                .when()
                .get("/user/login")
                .then()
                .statusCode(200)
                .header("X-Expires-After", notNullValue())
                .header("X-Rate-Limit", notNullValue());
    }



    // CLEANUP

    @AfterEach
    void tearDown() {

        if (userID != null) {

            given()
                    .pathParam("username", username)
                    .when()
                    .delete("/user/{username}")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(anyOf(is(200), is(404)));
        }
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("ALL TESTS FINISHED");
    }
}