package com.bingbong.banktransactionanalyzer;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

// 한 곳에 모든 로직이 있는 갓 클래스
// CSV 파일이 아니라 JSON 파일이라면 또 다시 만들어야하나?
public class BankTransactionAnalyzerSimpleTwo {

    private static final String PROJECT_NAME = "real-world-software-development/";
    private static final String RESOURCES = "src/main/resources/";

    public static void main(final String[] args) throws Exception {

        final BankStatementCSVParser bankStatementCSVParser = new BankStatementCSVParser();

         final Path path = Paths.get(PROJECT_NAME + RESOURCES + "bank-data.csv");
        final List<String> lines = Files.readAllLines(path);
        List<BankTransaction> bankTransactions = bankStatementCSVParser.parseLineFromCSV(lines);

        System.out.println("total : " + calculateTotalAmount(bankTransactions));
        System.out.println("1 total : " + selectInMonth(bankTransactions, Month.JANUARY));
    }

    public static double calculateTotalAmount(final List<BankTransaction> bankTransactions) {
        double total = 0d;
        for(final BankTransaction bankTransaction : bankTransactions) {
            total += bankTransaction.getAmount();
        }
        return total;
    }


    public static List<BankTransaction> selectInMonth(final List<BankTransaction> bankTransactions, final
        Month month) {
        final List<BankTransaction> bankTransactionsInMonth = new ArrayList<>();
        for (BankTransaction bankTransaction : bankTransactions) {
            if (bankTransaction.getDate().getMonth() == month) {
                bankTransactionsInMonth.add(bankTransaction);
            }
        }
        return bankTransactions;
    }


}
