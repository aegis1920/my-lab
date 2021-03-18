package com.bingbong.effectivejava.item65;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class JustObjectTest {

    @Test
    void classTest() throws ClassNotFoundException {
        Class justObjectClass = JustObject.class;
        Class justObjectClassForName = Class.forName("com.bingbong.effectivejava.item65.JustObject");

        assertThat(justObjectClass).isEqualTo(justObjectClassForName);
    }

    @Test
    void constructorTest() {
        Class justObjectClass = JustObject.class;
        Constructor[] constructors = justObjectClass.getDeclaredConstructors();

        assertThat(constructors[0].getName()).contains("JustObject");
    }

    @Test
    void methodTest() {
        Class justObjectClass = JustObject.class;
        Method[] methods = justObjectClass.getDeclaredMethods();

        assertThat(methods).hasSize(2);
    }

    @Test
    void fieldTest() {
        Class justObjectClass = JustObject.class;
        Field[] fields = justObjectClass.getDeclaredFields();

        assertThat(fields).hasSize(3);
    }

    @Test
    void fieldTest2() {
        Class justObjectClass = JustObject.class;
        Field[] fields = justObjectClass.getDeclaredFields();

        assertThat(Arrays.stream(fields).map(Field::getName))
            .containsExactly("constructorField", "privateField", "publicField");
    }
}
