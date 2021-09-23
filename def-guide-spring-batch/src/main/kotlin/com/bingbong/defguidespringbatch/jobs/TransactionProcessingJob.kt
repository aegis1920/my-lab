package com.bingbong.defguidespringbatch.jobs

import com.bingbong.defguidespringbatch.domain.AccountSummary
import com.bingbong.defguidespringbatch.domain.Transaction
import com.bingbong.defguidespringbatch.domain.TransactionDao
import com.bingbong.defguidespringbatch.domain.support.TransactionDaoSupport
import com.bingbong.defguidespringbatch.processor.TransactionApplierProcessor
import com.bingbong.defguidespringbatch.reader.TransactionReader
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor
import org.springframework.batch.item.file.transform.DelimitedLineAggregator
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.transform.FieldSet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.Resource
import javax.sql.DataSource


@EnableBatchProcessing
@SpringBootApplication
class TransactionProcessingJob(
    @Autowired private val jobBuilderFactory: JobBuilderFactory,
    @Autowired private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    @StepScope
    fun transactionReader(): TransactionReader {
        return TransactionReader(fileItemReader(null))
    }

    @Bean
    @StepScope
    fun fileItemReader(@Value("#{jobParameters['transactionFile']}") inputFile: Resource): FlatFileItemReader<FieldSet> =
        FlatFileItemReaderBuilder<FieldSet>()
            .name("fileItemReader")
            .resource(inputFile)
            .lineTokenizer(DelimitedLineTokenizer())
            .fieldSetMapper(PassThroughFieldSetMapper())
            .build()
    @Bean
    fun importTransactionFileStep() = this.stepBuilderFactory.get("importTransactionFileStep")
        .chunk<Transaction, Transaction>(100)
        .reader(transactionReader())
        .writer(transactionWriter(null))
        .allowStartIfComplete(true)
        .listener(transactionReader())
        .build()

    @Bean
    fun transactionWriter(dataSource: DataSource?): JdbcBatchItemWriter<Transaction> =
        JdbcBatchItemWriterBuilder<Transaction>()
            .itemSqlParameterSourceProvider(
                BeanPropertyItemSqlParameterSourceProvider()
            )
            .sql(
                "INSERT INTO TRANSACTION " +
                        "(ACCOUNT_SUMMARY_ID, TIMESTAMP, AMOUNT) " +
                        "VALUES ((SELECT ID FROM ACCOUNT_SUMMARY " +
                        "	WHERE ACCOUNT_NUMBER = :accountNumber), " +
                        ":timestamp, :amount)"
            )
            .dataSource(dataSource)
            .build()

    @Bean
    @StepScope
    fun accountSummaryReader(dataSource: DataSource?): JdbcCursorItemReader<AccountSummary> {
        return JdbcCursorItemReaderBuilder<AccountSummary>()
            .name("accountSummaryReader")
            .dataSource(dataSource)
            .sql(
                "SELECT ACCOUNT_NUMBER, CURRENT_BALANCE " +
                        "FROM ACCOUNT_SUMMARY A " +
                        "WHERE A.ID IN (" +
                        "	SELECT DISTINCT T.ACCOUNT_SUMMARY_ID " +
                        "	FROM TRANSACTION T) " +
                        "ORDER BY A.ACCOUNT_NUMBER"
            )
            .rowMapper { resultSet, _ ->
                AccountSummary(
                    accountNumber = resultSet.getString("account_number"),
                    currentBalance = resultSet.getDouble("current_balance"),
                )
            }.build()
    }

    @Bean
    fun transactionDao(dataSource: DataSource?): TransactionDao {
        return TransactionDaoSupport(dataSource)
    }

    @Bean
    fun transactionApplierProcessor(): TransactionApplierProcessor {
        return TransactionApplierProcessor(transactionDao(null))
    }

    @Bean
    fun accountSummaryWriter(dataSource: DataSource?): JdbcBatchItemWriter<AccountSummary> {
        return JdbcBatchItemWriterBuilder<AccountSummary>()
            .dataSource(dataSource)
            .itemSqlParameterSourceProvider(
                BeanPropertyItemSqlParameterSourceProvider()
            )
            .sql(
                "UPDATE ACCOUNT_SUMMARY " +
                        "SET CURRENT_BALANCE = :currentBalance " +
                        "WHERE ACCOUNT_NUMBER = :accountNumber"
            )
            .build()
    }

    @Bean
    fun applyTransactionsStep(): Step {
        return stepBuilderFactory.get("applyTransactionsStep")
            .chunk<AccountSummary?, AccountSummary?>(100)
            .reader(accountSummaryReader(null))
            .processor(transactionApplierProcessor())
            .writer(accountSummaryWriter(null))
            .build()
    }

    @Bean
    @StepScope
    fun accountSummaryFileWriter(
        @Value("#{jobParameters['summaryFile']}") summaryFile: Resource?
    ): FlatFileItemWriter<AccountSummary> {
        val lineAggregator: DelimitedLineAggregator<AccountSummary> = DelimitedLineAggregator()
        val fieldExtractor: BeanWrapperFieldExtractor<AccountSummary> = BeanWrapperFieldExtractor()
        fieldExtractor.setNames(arrayOf("accountNumber", "currentBalance"))
        fieldExtractor.afterPropertiesSet()
        lineAggregator.setFieldExtractor(fieldExtractor)
        return FlatFileItemWriterBuilder<AccountSummary>()
            .name("accountSummaryFileWriter")
            .resource(summaryFile)
            .lineAggregator(lineAggregator)
            .build()
    }

    @Bean
    fun generateAccountSummaryStep(): Step {
        return stepBuilderFactory.get("generateAccountSummaryStep")
            .chunk<AccountSummary?, AccountSummary?>(100)
            .reader(accountSummaryReader(null))
            .writer(accountSummaryFileWriter(null))
            .build()
    }

    @Bean
    fun transactionJob(): Job {
        return jobBuilderFactory.get("transactionJob")
            .preventRestart()
            .start(importTransactionFileStep())
            .next(applyTransactionsStep())
            .next(generateAccountSummaryStep())
            .build()
        //		return this.jobBuilderFactory.get("transactionJob")
//				.start(importTransactionFileStep())
//				.on("STOPPED").stopAndRestart(importTransactionFileStep())
//				.from(importTransactionFileStep()).on("*").to(applyTransactionsStep())
//				.from(applyTransactionsStep()).next(generateAccountSummaryStep())
//				.end()
//				.build();
    }
}
