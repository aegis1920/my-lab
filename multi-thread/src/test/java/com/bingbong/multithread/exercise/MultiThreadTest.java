package com.bingbong.multithread.exercise;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MultiThreadTest {

    int sum;
    private AtomicInteger atomicSum;


    @BeforeEach
    void setUp() {
        sum = 0;
        atomicSum = new AtomicInteger();
    }

    @DisplayName("2개의 스레드로 1억을 만들기, 실패")
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

    @DisplayName("2개의 스레드로 1억을 만들기, synchronized을 이용해 Lock")
    @Test
    void twoThreadSumWithSynchronized() throws InterruptedException {
        Thread thread1 = new Thread(this::workerThreadWithSynchronized);
        Thread thread2 = new Thread(this::workerThreadWithSynchronized);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertThat(sum).isEqualTo(100_000_000);
    }

    @DisplayName("2개의 스레드로 1억을 만들기, AtomicInteger를 이용 (Lock이 아님)")
    @Test
    void twoThreadSumWithAtomic() throws InterruptedException {
        Thread thread1 = new Thread(this::workerThreadWithAtomic);
        Thread thread2 = new Thread(this::workerThreadWithAtomic);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertThat(atomicSum.get()).isEqualTo(100_000_000);
    }

    private void workerThread() {
        for (int i = 0; i < 25_000_000; i++) {
            sum += 2;
        }
    }

    private synchronized void workerThreadWithSynchronized() {
        for (int i = 0; i < 25_000_000; i++) {
            sum += 2;
        }
    }

    private void workerThreadWithAtomic() {
        for (int i = 0; i < 25_000_000; i++) {
            atomicSum.addAndGet(2);
        }
    }
}
