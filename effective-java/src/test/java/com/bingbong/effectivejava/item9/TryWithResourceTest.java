package com.bingbong.effectivejava.item9;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TryWithResourceTest {

    @DisplayName("finally에 있는 두 번째 예외만 나온다.")
    @Test
    void ThrownException_OnlyTryFinally() {
        assertThatThrownBy(this::methodByTryFinally)
            .isInstanceOf(SecondException.class);
    }

    @DisplayName("try에 있는 첫 번째 예외와 두 번째 예외 둘 다 나온다.")
    @Test
    void ThrownException_TryWithResource() {
        assertThatThrownBy(this::methodByTryWithResource)
            .isInstanceOf(FirstException.class)
            .hasSuppressedException(new SecondException("두 번째 예외입니다."));
    }

    private void methodByTryFinally() {
        CustomWriterWithoutAutoCloseable writer = new CustomWriterWithoutAutoCloseable();
        try {
            writer.write();
        } finally {
            writer.close();
        }
    }

    private void methodByTryWithResource() {
        try (CustomWriterWithAutoCloseable writer = new CustomWriterWithAutoCloseable()) {
            writer.write();
        } catch (Exception e) {
            throw e;
        }
    }
}

class CustomWriterWithoutAutoCloseable {

    public void write() {
        throw new FirstException("첫 번째 예외입니다.");

    }

    public void close() {
        throw new SecondException("두 번째 예외입니다.");
    }
}

class CustomWriterWithAutoCloseable implements AutoCloseable {

    public void write() {
        throw new FirstException("첫 번째 예외입니다.");

    }

    @Override
    public void close() {
        throw new SecondException("두 번째 예외입니다.");
    }
}

class FirstException extends RuntimeException {

    public FirstException(String message) {
        super(message);
    }
}

class SecondException extends RuntimeException {

    public SecondException(String message) {
        super(message);
    }
}
