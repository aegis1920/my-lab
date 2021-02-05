package com.bingbong.cache.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LastModifiedTest {

    private static final Logger logger = LoggerFactory.getLogger(LastModifiedTest.class);

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    // @formatter:off

    @DisplayName("/home/etag 경로 요청할 때 Cache-Control 및 Last-Modified 자동 적용")
    @Test
    void requestWithLastModifiedTest() {
        ExtractableResponse<Response> response = given()
            .when()
                .get("/home/etag")
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

        String lastModified = response.header("Last-Modified");

        assertThat(lastModified).isNotNull();

        logger.debug("Last-Modified : {}", lastModified);

        // 브라우저에서 자동으로 If-Modified-Since 헤더를 붙여준다.
        given()
            .header("If-Modified-Since", lastModified)
        .when()
            .get("/home/etag")
        .then()
            .statusCode(HttpStatus.NOT_MODIFIED.value())
            .extract();
    }

    @DisplayName("nothing.html에는 LastModified가 적용되지 않음")
    @Test
    void requestWithoutLastModifiedTest() {
        ExtractableResponse<Response> response = given()
            .when()
                .get("/home/nothing")
            .then()
                .statusCode(HttpStatus.OK.value())
                .extract();

        String lastModified = response.header("Last-Modified");

        assertThat(lastModified).isNull();
    }
    // @formatter:on
}
