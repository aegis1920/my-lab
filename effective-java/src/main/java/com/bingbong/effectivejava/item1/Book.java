package com.bingbong.effectivejava.item1;

public class Book {

    private static final Book DEFAULT_BOOK = new Book("default", "default");

    private String name;
    private String author;

    // 생성자를 이용한 객체 초기화
    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }

    // 정적 팩토리 메서드를 이용한 객체 초기화
    // 메서드 명을 통해 생성해줄 때 이름을 가질 수 있다.
    public static Book createByName(String name) {
        return new Book(name, null);
    }

    // 정적 팩토리 메서드를 이용한 객체 초기화
    // 매개변수 개수와 타입이 같아도 메서드 명을 다르게 해서 여러 개를 만들 수 있다.
    public static Book createByAuthor(String author) {
        return new Book(null, author);
    }

    // 상수화를 통해 캐싱해둔 걸 이용할 수 있다. 즉, 매번 새로운 객체를 만들지 않아도 된다.
    // Boolean.valueOf(true)와 같다.
    public static Book createByDefault() {
        return DEFAULT_BOOK;
    }
}
