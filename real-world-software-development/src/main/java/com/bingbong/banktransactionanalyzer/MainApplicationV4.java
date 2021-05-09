package com.bingbong.banktransactionanalyzer;

public class MainApplicationV4 {

    public static void main(final String[] args) throws Exception {

        final BankStatementAnalyzer bankStatementAnalyzer = new BankStatementAnalyzer();
        final BankStatementCSVParserV2 bankStatementCSVParser = new BankStatementCSVParserV2();

        bankStatementAnalyzer.analyze("bank-data.csv", bankStatementCSVParser);
    }
}
