package com.bingbong.effectivejava.item32;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class PickerTest {

    @Test
    void pickTwoTest() {
        assertThatThrownBy(() -> {
            String[] attributes = Picker.pickTwo("Good", "Fast", "Cheap");
            System.out.println(Arrays.toString(attributes));
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    void safePickTwoTest() {
            List<String> attributes = Picker.safePickTwo("Good", "Fast", "Cheap");

            assertThat(attributes).hasSize(2);
    }
}
