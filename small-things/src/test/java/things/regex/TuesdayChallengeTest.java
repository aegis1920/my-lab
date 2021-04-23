package things.regex;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// 정답 출처 : https://github.com/cindyaddoil/Regex-Tuesday-Challenge
class TuesdayChallengeTest {

    @DisplayName("반복되는 단어를 <strong>으로 감싸기")
    @ParameterizedTest
    @CsvSource({"This is is a test,This is <strong>is</strong> a test", "This test test is is,This test <strong>test</strong> is <strong>is</strong>"})
    void weekOne(String input, String output) {
        // \b는 word를 의미. \S는 화이트 스페이스 캐릭터가 아닌 것들. + 이므로 해당 단어
        // \1은 첫 번째 그룹을 의미. 그래서 반복되는 단어 체크
        // 즉, 공백 기준으로 반복되는 단어 체크
        String pattern = "\\b(\\S+) (\\1)\\b";
        String replacement = "$1 <strong>$2</strong>";

        assertThat(input.replaceAll(pattern, replacement)).isEqualTo(output);
    }
}
