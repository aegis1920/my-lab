package things.character;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CharacterTest {
    @Test
    void 캐릭터_문자열_테스트() {
        String text = "안녕";

        final List<Integer> charCodePoints = text.codePoints().boxed().collect(Collectors.toList());

        assertThat(Character.toChars(charCodePoints.get(0))[0]).isEqualTo('안');
        assertThat(Character.toChars(charCodePoints.get(1))[0]).isEqualTo('녕');
    }
}
