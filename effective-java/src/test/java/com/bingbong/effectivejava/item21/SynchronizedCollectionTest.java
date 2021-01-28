package com.bingbong.effectivejava.item21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.collection.SynchronizedCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SynchronizedCollectionTest {

    @DisplayName("싱글 스레드 환경일 때는 잘된다. removeIf가 락에 대한 관리가 없을뿐")
    @Test
    void singleThreadTest() {
        List<String> names = new ArrayList<>(Arrays.asList("bingbong1", "bingbong2", "bingbong3"));

        SynchronizedCollection<String> sc = SynchronizedCollection.synchronizedCollection(names);

        sc.forEach(System.out::println);
        sc.removeIf(it -> it.equals("bingbong1"));
        sc.forEach(System.out::println);
    }

    // 멀티스레드를 어떻게 테스트해야할까? 멀티스레드 환경이면 ConcurrentModificationException가 일어날 수 있다

//    @Test
//    void multiThreadTest() {
//        List<String> names = new ArrayList<>(Arrays.asList("bingbong1", "bingbong2", "bingbong3"));
//
//        SynchronizedCollection<String> sc = SynchronizedCollection.synchronizedCollection(names);
//
//        int threadAmount = 100;
//
//        sc.forEach(System.out::println);
//
//        ExecutorService executorService = Executors.newFixedThreadPool(threadAmount);
//
//        ConcurrentModificationException
//        for (int i = 0; i < threadAmount; i++) {
//            executorService.execute(() -> System.out.println(sc.removeIf(it -> it.equals("bingbong1"))));
//        }
//
//        sc.forEach(System.out::println);
//    }
}
