package things.semaphore;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

import static org.assertj.core.api.Assertions.assertThat;

class SemaphoreTest {

    @Test
    void semaphoreTest() {
        Semaphore semaphore = new Semaphore(1);

        assertThat(semaphore.availablePermits()).isEqualTo(1);
        assertThat(semaphore.tryAcquire()).isTrue();
        assertThat(semaphore.availablePermits()).isZero();
        semaphore.release();
        assertThat(semaphore.availablePermits()).isEqualTo(1);
        assertThat(semaphore.tryAcquire()).isTrue();
        // 여기서 실제로 semaphore.acquire()를 해버리면 UNSAFE.park()로 Thread.wait()과 같은 상태가 된다.
        assertThat(semaphore.tryAcquire()).isFalse();
    }
}