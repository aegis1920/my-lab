package com.bingbong.multithread.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MultiThreadTest {

    volatile int sum = 0;

    @DisplayName("2개의 스레드로 1억을 만들면 다른 값이 나오는 테스트")
    @Test
    void twoThreadSum() throws InterruptedException {
        Thread thread1 = new Thread(this::workerThread);
        Thread thread2 = new Thread(this::workerThread);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertThat(sum).isNotEqualTo(100_000_000);
    }

    private void workerThread() {
        for (int i = 0; i < 25_000_000; i++) {
            sum += 2;
        }
    }
}
