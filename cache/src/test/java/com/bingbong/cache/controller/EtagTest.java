package com.bingbong.cache.controller;

import static io.restassured.RestAssured.given;

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
class EtagTest {

    private static final Logger logger = LoggerFactory.getLogger(EtagTest.class);

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    // @formatter:off
    @DisplayName("/home/etag 경로 요청할 때 Cache-Control 및 Etag 적용")
    @Test
    void test() {
        ExtractableResponse<Response> beforeResponse = given()
            .when()
                .get("/home/etag")
            .then()
                .statusCode(HttpStatus.OK.value())
                .header("Cache-Control", "max-age=" + 60 * 60 * 24 * 365)
                .extract();

        String eTag = beforeResponse.header("ETag");

        logger.debug("eTag : {}", eTag);

        ExtractableResponse<Response> afterResponse = given()
               .header("If-None-Match", eTag)
            .when()
                .get("home/etag")
            .then()
                .statusCode(HttpStatus.NOT_MODIFIED.value())
                .extract();
    }
    // @formatter:on

}
