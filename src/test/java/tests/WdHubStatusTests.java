package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class WdHubStatusTests extends TestBase {

    @Test
    @DisplayName("Проверка успешного ответа 200")
    public void statusTest() {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("Проверка неуспешного ответа 401 при неуспешной авторизации")
    public void unauthorizedStatusTest() {
        given()
                .log().all()
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(401);
    }

    @Test
    @DisplayName("Проверка соответствия поля message cо значением")
    public void messageTextTest () {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.message", containsString("Selenoid v3.0.16 built at 2026-09-09_03:31:02PM"));
    }

    @Test
    @DisplayName("Проверка соответствия поля ready cо значением")
    public void readyTextTest () {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body("value.ready", equalTo(true));
    }

    @Test
    @DisplayName("Проверка соответствия json-схеме")
    public void statusSchemaTest () {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wd/hub/status")
                .then()
                .log().all()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/WdHubStatus.json"));
    }

    @Test
    @DisplayName("Проверка oшибки при невалидном пути")
    public void invalidMethodTest () {
        given()
                .log().all()
                .auth().basic("user1", "1234")
                .when()
                .get("/wds/hubs/statuss")
                .then()
                .log().all()
                .statusCode(404)
                .body(containsString("404 page not found"));;
    }

}
