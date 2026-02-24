package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for Persistence
 * Total: 10 points
 */
@DisplayName("🗂️ Persistência")
public class PersistenceTests extends BaseTest {

    @Test
    @TestScore(points = 10, weight = 1.0, description = "Dados são persistidos (H2 ou PostgreSQL/MySQL)", 
               category = "🗂️ Persistência")
    @DisplayName("Persistência - Dados são salvos e recuperados")
    public void testDataPersistence() {
        // Create a course
        String courseJson = """
            {
                "name": "Persistent Course"
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
        
        // Verify the course can be retrieved (data is persisted)
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200)
            .body("id", equalTo(courseId))
            .body("name", equalTo("Persistent Course"));
        
        // Verify it appears in the list
        given()
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .body("id", hasItem(courseId));
        
        // Update the course
        String updatedJson = """
            {
                "name": "Updated Persistent Course"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(updatedJson)
        .when()
            .put("/courses/" + courseId)
        .then()
            .statusCode(200);
        
        // Verify the update persisted
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200)
            .body("name", equalTo("Updated Persistent Course"));
        
        // Delete the course
        given()
        .when()
            .delete("/courses/" + courseId)
        .then()
            .statusCode(204);
        
        // Verify deletion persisted
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(404);
    }
}

// Made with Bob
