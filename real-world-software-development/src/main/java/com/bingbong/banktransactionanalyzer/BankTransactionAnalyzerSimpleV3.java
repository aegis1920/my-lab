package com.bingbong.banktransactionanalyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;

// 한 곳에 모든 로직이 있는 갓 클래스
// CSV 파일이 아니라 JSON 파일이라면 또 다시 만들어야하나?
public class BankTransactionAnalyzerSimpleV3 {

    private static final String PROJECT_NAME = "real-world-software-development/";
    private static final String RESOURCES = "src/main/resources/";
    private static final BankStatementCSVParser bankStatementCSVParser = new BankStatementCSVParser();

    public static void main(final String[] args) throws Exception {

        final Path path = Paths.get(PROJECT_NAME + RESOURCES + "bank-data.csv");
        final List<String> lines = Files.readAllLines(path);
        List<BankTransaction> bankTransactions = bankStatementCSVParser.parseLineFromCSV(lines);

        final BankStatementProcessor bankStatementProcessor = new BankStatementProcessor(
            bankTransactions);

        collectSummary(bankStatementProcessor);
    }

    private static void collectSummary(BankStatementProcessor bankStatementProcessor) {
        System.out.println(bankStatementProcessor.calculateTotalAmount());
        System.out.println(bankStatementProcessor.calculateTotalInMonth(Month.JANUARY));
        System.out.println(bankStatementProcessor.calculateTotalForCategory("Salary"));
    }
}
