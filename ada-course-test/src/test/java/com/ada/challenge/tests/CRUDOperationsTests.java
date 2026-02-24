package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for CRUD Operations
 * Total: 25 points (5 points each operation)
 */
@DisplayName("🔧 Requisitos Funcionais - CRUD")
public class CRUDOperationsTests extends BaseTest {

    @Test
    @TestScore(points = 5, weight = 0.5, description = "Criar curso (POST /courses)", category = "🔧 CRUD de Curso")
    @DisplayName("CRUD - Criar curso")
    public void testCreateCourse() {
        String courseJson = """
            {
                "name": "Java Programming"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("name", equalTo("Java Programming"));
    }

    @Test
    @TestScore(points = 5, weight = 0.5, description = "Listar todos os cursos (GET /courses)", category = "🔧 CRUD de Curso")
    @DisplayName("CRUD - Listar cursos")
    public void testListAllCourses() {
        // Create a course first
        String courseJson = """
            {
                "name": "Python Basics"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(courseJson)
        .when()
            .post("/courses");
        
        // List all courses
        given()
        .when()
            .get("/courses")
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("$", instanceOf(java.util.List.class))
            .body("size()", greaterThan(0));
    }

    @Test
    @TestScore(points = 5, weight = 0.5, description = "Buscar curso por ID (GET /courses/{id})", category = "🔧 CRUD de Curso")
    @DisplayName("CRUD - Buscar curso por ID")
    public void testGetCourseById() {
        // Create a course
        String courseJson = """
            {
                "name": "JavaScript Fundamentals"
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
        
        // Get the course by ID
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(200)
            .contentType("application/json")
            .body("id", equalTo(courseId))
            .body("name", equalTo("JavaScript Fundamentals"));
    }

    @Test
    @TestScore(points = 5, weight = 0.5, description = "Atualizar curso (PUT /courses/{id})", category = "🔧 CRUD de Curso")
    @DisplayName("CRUD - Atualizar curso")
    public void testUpdateCourse() {
        // Create a course
        String courseJson = """
            {
                "name": "Old Course Name"
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
        
        // Update the course
        String updatedJson = """
            {
                "name": "Updated Course Name"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(updatedJson)
        .when()
            .put("/courses/" + courseId)
        .then()
            .statusCode(200)
            .body("id", equalTo(courseId))
            .body("name", equalTo("Updated Course Name"));
    }

    @Test
    @TestScore(points = 5, weight = 0.5, description = "Remover curso (DELETE /courses/{id})", category = "🔧 CRUD de Curso")
    @DisplayName("CRUD - Remover curso")
    public void testDeleteCourse() {
        // Create a course
        String courseJson = """
            {
                "name": "Course to Delete"
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
        
        // Delete the course
        given()
        .when()
            .delete("/courses/" + courseId)
        .then()
            .statusCode(204);
        
        // Verify it's deleted
        given()
        .when()
            .get("/courses/" + courseId)
        .then()
            .statusCode(404);
    }
}

// Made with Bob
