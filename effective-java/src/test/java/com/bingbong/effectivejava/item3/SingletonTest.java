package com.bingbong.effectivejava.item3;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.Test;

class SingletonTest {

    @Test
    void enumSingletonTest() {
        assertThat(EnumSingleton.INSTANCE).isEqualTo(EnumSingleton.INSTANCE);
    }

    @Test
    void staticFactoryMethodSingletonTest() {
        assertThat(StaticFactoryMethodSingleton.getInstance()).isEqualTo(StaticFactoryMethodSingleton.getInstance());
    }

    @Test
    void publicStaticFinalSingletonTest() {
        assertThat(PublicStaticFinalSingleton.INSTANCE).isEqualTo(PublicStaticFinalSingleton.INSTANCE);
    }
}
