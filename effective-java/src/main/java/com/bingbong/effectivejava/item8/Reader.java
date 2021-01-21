package com.bingbong.effectivejava.item8;

import java.io.BufferedReader;
import java.io.IOException;

// 자원 회수를 위해 finalizer와 cleaner가 아닌 AutoCloseable을 구현해서 사용하자.
public class Reader implements AutoCloseable {

    private final BufferedReader bufferedReader;

    public Reader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String readFirstLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public void close() {
        try {
            bufferedReader.close();
            System.out.print("closed도 실행됐다!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
