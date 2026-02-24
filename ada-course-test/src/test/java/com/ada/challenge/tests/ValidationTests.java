package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for Bean Validation
 * Total: 15 points (Course: 10 points, Lesson: 5 points)
 */
@DisplayName("🧾 Regras de Validação")
public class ValidationTests extends BaseTest {

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Course.name é obrigatório", category = "🧾 Validações")
    @DisplayName("Validação - Course.name obrigatório")
    public void testCourseNameRequired() {
        String courseJson = """
            {
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(400);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Course.name não pode ser vazio", category = "🧾 Validações")
    @DisplayName("Validação - Course.name não vazio")
    public void testCourseNameNotEmpty() {
        String courseJson = """
            {
                "name": ""
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(400);
    }

    @Test
    @TestScore(points = 4, weight = 0.4, description = "Course.name mínimo 3 caracteres", category = "🧾 Validações")
    @DisplayName("Validação - Course.name mínimo 3 caracteres")
    public void testCourseNameMinLength() {
        // Test with 2 characters (should fail)
        String courseJson = """
            {
                "name": "AB"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(400);
        
        // Test with 3 characters (should succeed)
        String validCourseJson = """
            {
                "name": "ABC"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(validCourseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201);
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Lesson.name é obrigatório", category = "🧾 Validações")
    @DisplayName("Validação - Lesson.name obrigatório")
    public void testLessonNameRequired() {
        // Create a course first
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
        
        // Try to create lesson without name
        String lessonJson = """
            {
            }
            """;
        
        given()
            .contentType("application/json")
            .body(lessonJson)
        .when()
            .post("/courses/" + courseId + "/lessons")
        .then()
            .statusCode(400);
    }

    @Test
    @TestScore(points = 2, weight = 0.2, description = "Lesson.name não pode ser vazio", category = "🧾 Validações")
    @DisplayName("Validação - Lesson.name não vazio")
    public void testLessonNameNotEmpty() {
        // Create a course first
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
        
        // Try to create lesson with empty name
        String lessonJson = """
            {
                "name": ""
            }
            """;
        
        given()
            .contentType("application/json")
            .body(lessonJson)
        .when()
            .post("/courses/" + courseId + "/lessons")
        .then()
            .statusCode(400);
    }
}

// Made with Bob
