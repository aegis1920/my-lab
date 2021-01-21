package com.bingbong.effectivejava.item8;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReaderTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @DisplayName("AutoCloseable로 close가 실행된다")
    @Test
    void autoCloseable() throws IOException {
        BufferedReader testBufferedReader = new BufferedReader(
            new InputStreamReader(new ByteArrayInputStream("TEST DATA".getBytes())));

        try (Reader reader = new Reader(testBufferedReader)) {
            assertThat(reader.readFirstLine()).isEqualTo("TEST DATA");
        }
        assertThat(outContent).hasToString("closed도 실행됐다!");
    }
}
