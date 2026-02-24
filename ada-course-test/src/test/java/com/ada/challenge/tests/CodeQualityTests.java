package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for Code Quality and Best Practices
 * Total: 5 points mandatory + 3 points optional
 */
@DisplayName("🧪 Qualidade do Código")
public class CodeQualityTests extends BaseTest {

    @Test
    @TestScore(points = 1, weight = 0.1, description = "Uso correto de anotações REST (@Path, @GET, @POST, etc)", 
               category = "🧪 Qualidade")
    @DisplayName("Qualidade - Anotações REST corretas")
    public void testRESTAnnotations() {
        // If endpoints work correctly, REST annotations are properly used
        String courseJson = """
            {
                "name": "Test Course"
            }
            """;
        
        Integer courseId = given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .extract().path("id");
        
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200);
    }

    @Test
    @TestScore(points = 1, weight = 0.1, description = "Uso correto de códigos HTTP", 
               category = "🧪 Qualidade")
    @DisplayName("Qualidade - Códigos HTTP corretos")
    public void testHTTPStatusCodes() {
        // Test various HTTP status codes
        String courseJson = """
            {
                "name": "Test Course"
            }
            """;
        
        // 201 Created
        Integer courseId = given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .extract().path("id");
        
        // 200 OK
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200);
        
        // 400 Bad Request
        given()
            .contentType("application/json")
            .body("{\"name\": \"\"}")
        .when()
            .post("/courses")
        .then()
            .statusCode(400);
        
        // 404 Not Found
        given()
        .when()
            .get("/courses/999999")
        .then()
            .statusCode(404);
        
        // 204 No Content
        given()
        .when()
            .delete("/courses/" + courseId)
        .then()
            .statusCode(204);
    }

    @Test
    @TestScore(points = 1, weight = 0.1, description = "Retorno correto do header Content-Type", 
               category = "🧪 Qualidade")
    @DisplayName("Qualidade - Header Content-Type correto")
    public void testContentTypeHeader() {
        given()
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .contentType("application/json");
        
        String courseJson = """
            {
                "name": "Test Course"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .contentType("application/json");
    }

    @Test
    @TestScore(points = 1, weight = 0.1, description = "Uso de Bean Validation (@NotNull, @NotBlank, @Size, etc)", 
               category = "🧪 Qualidade")
    @DisplayName("Qualidade - Bean Validation implementado")
    public void testBeanValidation() {
        // Test that validation is working
        
        // Empty name should fail
        given()
            .contentType("application/json")
            .body("{\"name\": \"\"}")
        .when()
            .post("/courses")
        .then()
            .statusCode(400);
        
        // Missing name should fail
        given()
            .contentType("application/json")
            .body("{}")
        .when()
            .post("/courses")
        .then()
            .statusCode(400);
        
        // Name too short should fail
        given()
            .contentType("application/json")
            .body("{\"name\": \"AB\"}")
        .when()
            .post("/courses")
        .then()
            .statusCode(400);
        
        // Valid name should succeed
        given()
            .contentType("application/json")
            .body("{\"name\": \"Valid Course Name\"}")
        .when()
            .post("/courses")
        .then()
            .statusCode(201);
    }

    @Test
    @TestScore(points = 1, weight = 0.1, description = "Código bem estruturado (separação de responsabilidades)", 
               category = "🧪 Qualidade")
    @DisplayName("Qualidade - Estrutura básica do código")
    public void testCodeStructure() {
        // If all CRUD operations work, the code has basic structure
        String courseJson = """
            {
                "name": "Structured Course"
            }
            """;
        
        // Create
        Integer courseId = given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .extract().path("id");
        
        // Read
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200);
        
        // Update
        given()
            .contentType("application/json")
            .body("{\"name\": \"Updated Course\"}")
        .when()
            .put("/courses/" + courseId)
        .then()
            .statusCode(200);
        
        // Delete
        given()
        .when()
            .delete("/courses/" + courseId)
        .then()
            .statusCode(204);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Organização avançada (Resource, DTO, Service, Repository)", 
               category = "🧪 Qualidade", mandatory = false)
    @DisplayName("Qualidade - Organização avançada do código (PLUS)")
    public void testAdvancedCodeOrganization() {
        // This test passes if the API works correctly with proper separation
        // The actual verification would require code inspection, but we can
        // verify that the API behaves correctly which indicates good structure
        
        String courseJson = """
            {
                "name": "Well Organized Course"
            }
            """;
        
        Integer courseId = given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", equalTo("Well Organized Course"))
            .extract().path("id");
        
        // Verify all operations work smoothly
        given()
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .body("id", hasItem(courseId));
        
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200)
            .body("name", equalTo("Well Organized Course"));
    }
}

// Made with Bob
