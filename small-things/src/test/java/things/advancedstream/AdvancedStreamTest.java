package things.advancedstream;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdvancedStreamTest {

    List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
            new User("hello1", 20, "guest"),
            new User("hello2", 15, "guest"),
            new User("hello3", 30, "master"),
            new User("hello4", 11, "master"),
            new User("hello5", 40, "master")
        );
    }

    @DisplayName("List 개수 카운팅하기")
    @Test
    void counting() {
        Long count1 = users.stream().collect(Collectors.counting());
        long count2 = users.stream().count();
        int count3 = users.size();

        assertThat(count1).isEqualTo(count2);
        assertThat(count2).isEqualTo(count3);
    }

    @DisplayName("List에서 최댓값 구하기")
    @Test
    void maxBy() {
        Optional<User> maxUser1 = users.stream()
            .collect(Collectors.maxBy(Comparator.comparingInt(User::getAge)));
        Optional<User> maxUser2 = users.stream()
            .max(Comparator.comparingInt(User::getAge));

        assertThat(maxUser1).isEqualTo(maxUser2);
    }

    @DisplayName("List에서 합계 구하기")
    @Test
    void sum() {
        Integer sum1 = users.stream()
            .collect(Collectors.summingInt(User::getAge));
        Integer sum2 = users.stream().mapToInt(User::getAge).sum();

        assertThat(sum1).isEqualTo(sum2);
    }

    @DisplayName("List에서 평균 구하기")
    @Test
    void average() {
        Double average1 = users.stream()
            .collect(Collectors.averagingInt(User::getAge));

        assertThat(average1).isNotNull();
    }

    @DisplayName("List에서 전체 통계 구하기")
    @Test
    void statistics() {
        IntSummaryStatistics statistics = users.stream()
            .collect(Collectors.summarizingInt(User::getAge));

        assertThat(statistics).isNotNull();
    }

    @DisplayName("List에서 문자열로 join하기")
    @Test
    void join() {
        String joinString = users.stream()
            .map(User::getName)
            .collect(Collectors.joining(", "));

        assertThat(joinString).isNotNull();
    }

    @DisplayName("List에서 권한으로 그룹핑하기")
    @Test
    void groupingBy() {
        Map<String, List<User>> map = users.stream()
            .collect(Collectors.groupingBy(User::getAuthority));

        assertThat(map.entrySet().size()).isEqualTo(2);
    }
}
