package com.bingbong.effectivejava.item85;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.SerializationUtils.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SerializableTest {

    @DisplayName("Serializable을 구현한 Person 객체 직렬화 테스트")
    @Test
    void writeObjectTest() throws IOException {
        Person person = new Person("bingbong", 21);

        byte[] serializedPerson;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(person);
                // 직렬화된 Person 객체
                serializedPerson = baos.toByteArray();
            }
        }
        // 요런 식으로 나옴
        // -84, -19, 0, 5, 115, 114, 0, 57, 99, 111, 109, 46, 98, 105, 110, 103, 98, 111, 110, 103, 46, 101, 102, 102, 101, 99, 116, 105, 118, 101, 106, 97, 118, 97, 46, 105, 116, 101, 109, 56, 53, 46, 83, 101, 114, 105, 97, 108, 105, 122, 97, 98, 108, 101, 84, 101, 115, 116, 36, 80, 101, 114, 115, 111, 110, -126, 113, -121, -86, 125, 92, 57, 9, 2, 0, 2, 73, 0, 3, 97, 103, 101, 76, 0, 4, 110, 97, 109, 101, 116, 0, 18, 76, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 83, 116, 114, 105, 110, 103, 59, 120, 112, 0, 0, 0, 21, 116, 0, 8, 98, 105, 110, 103, 98, 111, 110, 103
        assertThat(serializedPerson).isNotEmpty();
    }

    @DisplayName("Serializable을 구현한 Person 객체 직렬화 후, 역직렬화 테스트")
    @Test
    void writeObjectTest2() throws IOException {
        Person person = new Person("bingbong", 21);

        byte[] serializedPerson;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(person);
                // 직렬화된 Person 객체
                serializedPerson = baos.toByteArray();
            }
        }

        Person deSerializedPerson = null;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedPerson)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                // 역직렬화된 Person 객체
                deSerializedPerson = (Person) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        assertThat(deSerializedPerson).isNotNull();
        assertThat(deSerializedPerson.name).isEqualTo("bingbong");
        assertThat(deSerializedPerson.age).isEqualTo(21);
    }

    @DisplayName("JSON 직렬화 테스트")
    @Test
    void jsonSerializable() {
        Person person = new Person("bingbong", 21);
        String json = String.format(
            "{\"name\":\"%s\",\"age\":%d}", person.name, person.age);

        assertThat(json).isEqualTo("{\"name\":\"bingbong\",\"age\":21}");
    }

    @DisplayName("역직렬화 폭탄 테스트")
    @Test
    void deserializeBomb() {
        byte[] bomb = bomb();
        // 역직렬화를 하면 엄청 많은 시간이 걸린다.

//        deserialize(bomb);
        assertThat(bomb).isNotEmpty();
    }

    static byte[] bomb() {
        Set<Object> root = new HashSet<>();
        Set<Object> s1 = root;
        Set<Object> s2 = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            Set<Object> t1 = new HashSet<>();
            Set<Object> t2 = new HashSet<>();
            t1.add("foo");
            s1.add(t1);
            s1.add(t2);
            s2.add(t1);
            s2.add(t2);
            s1 = t1;
            s2 = t2;
        }
        return serialize(root);
    }

    static class Person implements Serializable {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
