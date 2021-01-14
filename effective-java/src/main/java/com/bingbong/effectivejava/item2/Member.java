package com.bingbong.effectivejava.item2;

// setter를 사용하는 자바빈즈 패턴과는 다르게 final 선언으로 불변
// 빌더 패턴은 매개변수가 많을 때 코드가 길어지는 이외에 단점이 없다고 생각한다. 5개 이상일 때는 빌더패턴을 사용하자!
public class Member {
    // 필수
    private final String name;
    private final int age;

    // 옵셔널함
    private final String hobby;
    private final String mbti;

    // 객체는 반드시 Builder 객체로만 만들 수 있도록 하자.
    private Member(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.hobby = builder.hobby;
        this.mbti = builder.mbti;
    }

    // 롬복에서 사용하는 builder 처럼 하기
    public static Builder builder(String name, int age) {
        return new Builder(name, age);
    }

    public static class Builder {

        private final String name;
        private final int age;
        private String hobby;
        private String mbti;

        // 필수 매개변수부터 입력받자.
        public Builder(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // return this를 해줌으로써 메서드 체이닝을 해줄 수 있다.
        public Builder hobby(String hobby) {
            this.hobby = hobby;
            return this;
        }

        public Builder mbti(String mbti) {
            this.hobby = mbti;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
