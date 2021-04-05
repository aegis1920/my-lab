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

    @DisplayName("join을 하지 않은 thread, 계속 살아있으며 main Group")
    @Test
    void notJoinedThread() {
        Thread mainThread = Thread.currentThread();
        Thread newThread1 = new TimerThread(1);
        Thread newThread2 = new TimerThread(1);

        newThread1.start();
        newThread2.start();

        assertThat(mainThread.getId()).isGreaterThan(0L);
        assertThat(mainThread.getName()).isEqualTo("Test worker");
        assertThat(mainThread.getThreadGroup().getName()).isEqualTo("main");
        assertThat(mainThread.isAlive()).isTrue();

        assertThat(newThread1.getId()).isGreaterThan(0L);
        assertThat(newThread1.getThreadGroup().getName()).isEqualTo("main");
        assertThat(newThread1.getName()).contains("Thread-");
        assertThat(newThread1.isAlive()).isTrue();

        assertThat(newThread2.getId()).isGreaterThan(0L);
        assertThat(newThread2.getThreadGroup().getName()).isEqualTo("main");
        assertThat(newThread2.getName()).contains("Thread-");
        assertThat(newThread2.isAlive()).isTrue();
    }

    @DisplayName("join한 thread, 각 스레드는 실행이 완료되면 죽고 Group이 null임")
    @Test
    void joinedThread() throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        Thread newThread1 = new TimerThread(1);
        Thread newThread2 = new TimerThread(1);

        newThread1.start();
        newThread2.start();

        newThread1.join();
        newThread2.join();

        assertThat(mainThread.getId()).isGreaterThan(0L);
        assertThat(mainThread.getName()).isEqualTo("Test worker");
        assertThat(mainThread.getThreadGroup().getName()).isEqualTo("main");
        assertThat(mainThread.isAlive()).isTrue();

        assertThat(newThread1.getId()).isGreaterThan(0L);
        assertThat(newThread1.getThreadGroup()).isNull();
        assertThat(newThread1.getName()).contains("Thread-");
        assertThat(newThread1.isAlive()).isFalse();

        assertThat(newThread2.getId()).isGreaterThan(0L);
        assertThat(newThread2.getThreadGroup()).isNull();
        assertThat(newThread2.getName()).contains("Thread-");
        assertThat(newThread2.isAlive()).isFalse();
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

    static class TimerThread extends Thread {

        public int count;

        TimerThread(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(count * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
