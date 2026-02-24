package com.ada.challenge.tests;

import com.ada.challenge.scoring.TestScore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Tests for Model Data requirements
 * Total: 15 points (Course: 10 points, Lesson: 5 points)
 */
@DisplayName("📦 Modelo de Dados")
public class ModelDataTests extends BaseTest {

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Course deve ter campo 'id' (Long)", category = "📦 Modelo de Dados")
    @DisplayName("Course - Campo id")
    public void testCourseHasIdField() {
        // Create a course and verify it returns an id
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
            .body("id", notNullValue())
            .body("id", instanceOf(Number.class));
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Course deve ter campo 'name' (String)", category = "📦 Modelo de Dados")
    @DisplayName("Course - Campo name")
    public void testCourseHasNameField() {
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
            .body("name", equalTo("Test Course"))
            .body("name", instanceOf(String.class));
    }

    @Test
    @TestScore(points = 4, weight = 0.4, description = "Course deve ter campo 'lessons' (List<Lesson>)", category = "📦 Modelo de Dados")
    @DisplayName("Course - Campo lessons")
    public void testCourseHasLessonsField() {
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
            .body("lessons", notNullValue())
            .body("lessons", instanceOf(java.util.List.class));
    }

    @Test
    @TestScore(points = 2, weight = 0.2, description = "Lesson deve ter campo 'id' (Long)", category = "📦 Modelo de Dados")
    @DisplayName("Lesson - Campo id")
    public void testLessonHasIdField() {
        // First create a course
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
        
        // Then create a lesson
        String lessonJson = """
            {
                "name": "Test Lesson"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(lessonJson)
        .when()
            .post("/courses/" + courseId + "/lessons")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("id", instanceOf(Number.class));
    }

    @Test
    @TestScore(points = 3, weight = 0.3, description = "Lesson deve ter campo 'name' (String)", category = "📦 Modelo de Dados")
    @DisplayName("Lesson - Campo name")
    public void testLessonHasNameField() {
        // First create a course
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
        
        // Then create a lesson
        String lessonJson = """
            {
                "name": "Test Lesson"
            }
            """;
        
        given()
            .contentType("application/json")
            .body(lessonJson)
        .when()
            .post("/courses/" + courseId + "/lessons")
        .then()
            .statusCode(201)
            .body("name", equalTo("Test Lesson"))
            .body("name", instanceOf(String.class));
    }
}

// Made with Bob
