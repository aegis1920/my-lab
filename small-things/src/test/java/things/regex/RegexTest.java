package things.regex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RegexTest {

    @DisplayName("정규표현식을 사용하지 않은 도메인")
    @ParameterizedTest
    @ValueSource(strings = {"011-1234-1234", "010-12345-123", "ㅎ"})
    void regexTest_WithoutRegex(String input) {
        assertThatThrownBy(() -> new PhoneNumberWithoutRegex(input))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정규표현식을 사용한 도메인")
    @ParameterizedTest
    @ValueSource(strings = {"011-1234-1234", "010-12345-123", "ㅎ"})
    void regexTest_WithRegex(String input) {
        assertThatThrownBy(() -> new PhoneNumberWithRegex(input))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("하나의 Matcher를 사용한 정규표현식")
    @Test
    void regexTest_IfOneMatcher() {
        List<String> inputStrings = Arrays.asList("010-1234-1234", "010-1234-1235");

        Pattern pattern = Pattern.compile("010-\\d{4}-\\d{4}");
        Matcher matcher = pattern.matcher("");

        List<String> collect = inputStrings.stream()
            .filter(s -> matcher.reset(s).matches())
            .collect(Collectors.toList());

        assertThat(collect).hasSize(2);
    }

    // 이게 좀 더 Thread-safe하다.
    @DisplayName("매번 Matcher를 생성하는 정규표현식")
    @Test
    void regexTest_IfEveryTimeNewMatcher() {
        List<String> inputStrings = Arrays.asList("010-1234-1234", "010-1234-1235");

        Pattern pattern = Pattern.compile("010-\\d{4}-\\d{4}");

        List<String> collect = inputStrings.stream()
            .filter(s -> pattern.matcher(s).matches())
            .collect(Collectors.toList());

        assertThat(collect).hasSize(2);
    }

}
