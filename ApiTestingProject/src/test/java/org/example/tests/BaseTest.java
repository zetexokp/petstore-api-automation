package org.example.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    @BeforeAll
    static void setup() {
        System.out.println("Verify the initialization of resources for the subsequent tests (e.g., database connection)");
        String url = System.getProperty("base.url", "https://petstore.swagger.io/v2");
        RestAssured.baseURI = url;
    }

}
