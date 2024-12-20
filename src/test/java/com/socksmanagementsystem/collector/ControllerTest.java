package com.socksmanagementsystem.collector;

import com.socksmanagementsystem.dto.SockDto;
import io.restassured.http.ContentType;
import io.restassured.specification.MultiPartSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ControllerTest {

    @Test
    public void testAddSocksPositive() {
        SockDto sockRequest = new SockDto();
        sockRequest.setColor("red");
        sockRequest.setCottonPart(50);
        sockRequest.setQuantity(10);

        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body(sockRequest)
                .when()
                .post("/api/socks/income")
                .then()
                .statusCode(200)
                .body("color", equalTo("red"))
                .body("cottonPart", equalTo(50))
                .body("quantity", equalTo(10))
                .log().all();
    }

    @Test
    public void testAddSocksNegative() {
        SockDto sockRequest = new SockDto();
        sockRequest.setColor("red");
        sockRequest.setCottonPart(-50);  // Invalid cotton part
        sockRequest.setQuantity(10);

        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body(sockRequest)
                .when()
                .post("/api/socks/income")
                .then()
                .statusCode(400)  // Expecting a 400 Bad Request
                .log().all();
    }

    @Test
    public void testRemoveSocksPositive() {
        SockDto sockRequest = new SockDto();
        sockRequest.setColor("blue");
        sockRequest.setCottonPart(40);
        sockRequest.setQuantity(5);

        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body(sockRequest)
                .when()
                .post("/api/socks/outcome")
                .then()
                .statusCode(200)
                .body("color", equalTo("blue"))
                .body("cottonPart", equalTo(40))
                .body("quantity", equalTo(5))
                .log().all();
    }

    @Test
    public void testRemoveSocksNegative() {
        SockDto sockRequest = new SockDto();
        sockRequest.setColor("blue");
        sockRequest.setCottonPart(40);
        sockRequest.setQuantity(500);  // Invalid quantity

        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body(sockRequest)
                .when()
                .post("/api/socks/outcome")
                .then()
                .statusCode(400)  // Expecting a 400 Bad Request
                .log().all();
    }

    @Test
    public void testFilterSocksPositive() {
        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .param("color", "red")
                .param("operator", "moreThan")
                .param("cottonPart", 50)
                .when()
                .get("/api/socks")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .log().all();
    }

    @Test
    public void testFilterSocksNegative() {
        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .param("color", "blue")
                .param("operator", "lessThan")
                .param("cottonPart", 0)  // Invalid filter parameters
                .when()
                .get("/api/socks")
                .then()
                .statusCode(400)  // Expecting a 400 Bad Request
                .log().all();
    }

    @Test
    public void testUpdateSocksPositive() {
        SockDto sockRequest = new SockDto();
        sockRequest.setColor("green");
        sockRequest.setCottonPart(60);
        sockRequest.setQuantity(20);

        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body(sockRequest)
                .when()
                .put("/api/socks/1")
                .then()
                .statusCode(200)
                .body("color", equalTo("green"))
                .body("cottonPart", equalTo(60))
                .body("quantity", equalTo(20))
                .log().all();
    }

    @Test
    public void testUpdateSocksNegative() {
        SockDto sockRequest = new SockDto();
        sockRequest.setColor("green");
        sockRequest.setCottonPart(60);
        sockRequest.setQuantity(-20);  // Invalid quantity

        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.JSON)
                .body(sockRequest)
                .when()
                .put("/api/socks/999")  // Invalid ID
                .then()
                .statusCode(404)  // Expecting a 404 Not Found
                .log().all();
    }

    @Test
    public void testAddBatchSocksPositive() throws IOException {
        File file = new File("src/test/resources/socks.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockFile = new MockMultipartFile("file", "socks.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", fileInputStream);

        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.MULTIPART)
                .multiPart((MultiPartSpecification) mockFile)
                .when()
                .post("/api/socks/batch")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .log().all();
    }

    @Test
    public void testAddBatchSocksNegative() throws IOException {
        File file = new File("src/test/resources/invalid_socks.xlsx");  // Invalid file
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockFile = new MockMultipartFile("file", "invalid_socks.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", fileInputStream);

        given()
                .baseUri("http://localhost:8080")
                .contentType(ContentType.MULTIPART)
                .multiPart((MultiPartSpecification) mockFile)
                .when()
                .post("/api/socks/batch")
                .then()
                .statusCode(400)  // Expecting a 400 Bad Request
                .log().all();
    }
}



