package com.bingbong.banktransactionanalyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

// 한 곳에 모든 로직이 있는 갓 클래스
// CSV 파일이 아니라 JSON 파일이라면 또 다시 만들어야하나?
public class BankTransactionAnalyzerSimpleV1 {

    private static final String PROJECT_NAME = "real-world-software-development/";
    private static final String RESOURCES = "src/main/resources/";

    public static void main(final String[] args) throws Exception {
        final Path path = Paths.get(PROJECT_NAME + RESOURCES + "bank-data.csv");
        final List<String> lines = Files.readAllLines(path);
        double total = 0d;
        for (final String line : lines) {
            final String[] columns = line.split(",");
            final double amount = Double.parseDouble(columns[1]);
            total += amount;
        }

        System.out.println("total : " + total);
    }
}
