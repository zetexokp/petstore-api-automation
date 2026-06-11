package org.example.tests;

import io.restassured.http.ContentType;
import model.Pet;
import org.junit.jupiter.api.*;
import org.example.helper.Helper;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class PetStoreTest extends BaseTest {
    private Pet newPet;
    Long createdId;

    @BeforeEach
    void init() {
        System.out.println("START: adding a new pet to the store");

        newPet = Helper.createDefaultPet();
        createdId = newPet.id;

        given() // preparing to make a request
                .log().ifValidationFails() // Log only on failure
                .contentType(ContentType.JSON) // serialize newPet to JSON
                .body(newPet) // put the pet object into the request body

                .when() // send the request
                .post("/pet") // execute POST /pet

                .then() // validate the response
                .log().ifValidationFails() // Log only on failure
                .statusCode(200); // verify that the server returns HTTP 200 (OK)


        System.out.println("END: new pet was add to store, code 200, petID: " + createdId);
    }

    // Positive tests

    @Test
    @DisplayName("Verify adding a new pet to the store")
    void testAddANewPet() { }

    @Test
    @DisplayName("Verifying image upload")
    void testUploadAnImage(){

        byte[] imageBytes = new byte[1024];

        given()
                .log().ifValidationFails()
                .pathParam("petId", createdId)
                .multiPart("file", "my_image.png", imageBytes, "image/png") // Указываем имя и тип
                .multiPart("additionalMetadata", "my generated photo")
                .when()
                .post("/pet/{petId}/uploadImage")
                .then()
                .log().ifValidationFails()
                .statusCode(200);

        System.out.println("END: new image was upload, code 200, petID: " + createdId);
    }

    @Test
    @DisplayName("Verify updating an existing pet")
    void testUpdateAnExistingPet() {

        newPet.setName("UpdatedName");

        given()
                .contentType(ContentType.JSON)
                .body(newPet)
                .when()
                .put("/pet")
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/pet/" + createdId)
                .then()
                .statusCode(200)
                .body("name", equalTo("UpdatedName"));
    }

    @Test
    @DisplayName("Verify find pets by status")
    void testFindPetsByStatus() {

        given()
                .queryParam("status", "available")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                .body("status", everyItem(equalTo("available")));
    }

    @Test
    @DisplayName("Verify find pet by id")
    void testFindPetById() {

        given()
                .when()
                .get("/pet/" + createdId)
                .then()
                .statusCode(200)
                .body("id", equalTo(createdId.intValue()))
                .body("name", equalTo(newPet.getName()))
                .body("status", equalTo(newPet.status));
    }

    @Test
    @DisplayName("Verify updating pet using form data")
    void testUpdatePetWithFormData() {

        given()
                .contentType(ContentType.URLENC)
                .formParam("name", "UpdatedName")
                .formParam("status", "sold")
                .when()
                .post("/pet/" + createdId)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Verify delete pet by id")
    void testDeletePetById() {

        given()
                .when()
                .delete("/pet/" + createdId)
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/pet/" + createdId)
                .then()
                .statusCode(404);
    }

    // Negative tests

    @Test
    @DisplayName("Verify upload image without file")
    void testUploadImageWithoutFile() {

        given()
                .contentType(ContentType.MULTIPART)
                .pathParam("petId", createdId)
                .when()
                .post("/pet/{petId}/uploadImage")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    @DisplayName("Verify find pets with invalid status returns empty list")
    void testFindByInvalidStatus() {

        given()
                .queryParam("status", "unknown")
                .when()
                .get("/pet/findByStatus")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    @DisplayName("Verify update pet with invalid body")
    void testUpdatePetInvalidBody() {

        String invalidIdJson = "{ \"id\": \"abc\", \"name\": \"doggie\" }";

        given()
                .contentType(ContentType.JSON)
                .body(invalidIdJson)
                .when()
                .put("/pet")
                .then()
                .statusCode(anyOf(is(400), is(500)));
    }

    @Test
    @DisplayName("Verify delete non-existing pet returns 404")
    void testDeleteNonExistingPet() {

        given()
                .when()
                .delete("/pet/999999999")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Verify get non-existing pet returns 404")
    void testGetNonExistingPet() {

        given()
                .when()
                .get("/pet/999999999")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Verify create pet with empty body")
    void testCreatePetEmptyBody() {

        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/pet")
                .then()
                .statusCode(200);
    }




    @AfterEach
    void tearDown() {
        if (createdId != null) {
            System.out.println("CLEANUP: deleting pet with ID " + createdId);

            given()
                    .delete("/pet/" + createdId)
                    .then()
                    .statusCode(anyOf(is(200), is(404)));

            System.out.println("CLEANUP: pet was deleted");
        }
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Clean up all data after all tests");

    }



}