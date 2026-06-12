package org.example.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Order;
import org.example.helper.Helper;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreTest extends BaseTest {

    private Order newOrder;
    private Long orderId;

    private static final long NON_EXISTING_ID = 999999999L;

    // SETUP

    @BeforeEach
    void setUp() {

        newOrder = Helper.createDefaultOrder();

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .body(newOrder)
                        .when()
                        .post("/store/order");

        response.then()
                .statusCode(200);

        orderId = response.jsonPath().getLong("id");
    }

    // POSITIVE

    @Test
    void getOrderByValidIdShouldReturnOrderData() {

        given()
                .pathParam("orderId", orderId)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(orderId.intValue()));
    }

    @Test
    void getOrderShouldReturnJson() {

        given()
                .pathParam("orderId", orderId)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    void getInventoryShouldReturn200() {

        given()
                .when()
                .get("/store/inventory")
                .then()
                .statusCode(200);
    }

    @Test
    void createOrderShouldReturn200() {

        Order order = Helper.createDefaultOrder();

        given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .statusCode(200);
    }

    // NEGATIVE

    @Test
    void getOrderByZeroIdShouldReturn404() {

        given()
                .pathParam("orderId", 0)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(404);
    }

    @Test
    void getOrderByNegativeIdShouldReturn404() {

        given()
                .pathParam("orderId", -1)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(404);
    }

    @Test
    void getOrderByNonExistingIdShouldReturn404() {

        given()
                .pathParam("orderId", NON_EXISTING_ID)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .statusCode(404);
    }

    @Test
    void deleteNonExistingOrderShouldReturn404() {

        given()
                .pathParam("orderId", NON_EXISTING_ID)
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .statusCode(404);
    }

    @Test
    void createOrderWithEmptyBodyShouldReturn200() {

        given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when()
                .post("/store/order")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteNegativeIdShouldReturnError() {

        given()
                .pathParam("orderId", -1)
                .when()
                .delete("/store/order/{orderId}")
                .then()
                .statusCode(anyOf(is(400), is(404)));
    }

    // ================= CLEANUP =================

    @AfterEach
    void tearDown() {

        if (orderId != null) {

            given()
                    .pathParam("orderId", orderId)
                    .when()
                    .delete("/store/order/{orderId}")
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