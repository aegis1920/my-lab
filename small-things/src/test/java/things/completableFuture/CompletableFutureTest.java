package things.completableFuture;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * https://www.baeldung.com/java-completablefuture
 * https://www.callicoder.com/java-8-completablefuture-tutorial/
 */
class CompletableFutureTest {

    /**
     * CompletableFuture를 만들고 get()을 하면 Future가 Complete될 때까지 Block한다.
     * 그래서 아래 주석을 풀고 실행하면 Future가 Complete되지 않기 때문에 영원히 Block된다.
     * complete()를 통해 Future를 complete 시킬 수 있다.
     * 맨 처음에 오는 complete() 만 결과로 인정된다.
     */
    @Test
    void completableFutureNeverCompleted() throws ExecutionException, InterruptedException {
        String completedValue = "Hello World!";

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        assertThat(completableFuture.isDone()).isFalse();

//        completableFuture.get();
        completableFuture.complete(completedValue);
        completableFuture.complete("other Hello World!"); // 얘는 무시된다.

        assertThat(completableFuture.isDone()).isTrue();
        assertThat(completableFuture.isCancelled()).isFalse();
        assertThat(completableFuture.get()).isEqualTo(completedValue);
    }

    @DisplayName("이미 완료된 Future(값)라면 completedFuture static 메서드를 활용할 수 있음")
    @Test
    void threadTest2() throws ExecutionException, InterruptedException {
        String completedValue = "Hello World!";
        Future<String> completableFuture = CompletableFuture.completedFuture(completedValue);

        assertThat(completableFuture.get()).isEqualTo(completedValue);
    }

    @DisplayName("runAsync 메서드를 통해 다른 스레드에서 비동기로 실행")
    @Test
    void threadTest3() {
        Thread testThread = Thread.currentThread();

        // 다른 스레드에서 비동기로 실행
        CompletableFuture.runAsync(() -> {
            Thread completableFutureThread = Thread.currentThread();
            assertThat(testThread.getName()).isNotEqualTo(completableFutureThread.getName());
        });
    }
}
