package com.bingbong.defguidespringbatch.reader

import com.bingbong.defguidespringbatch.domain.Transaction
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.StepExecution
import org.springframework.batch.core.annotation.AfterStep
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemStreamReader
import org.springframework.batch.item.file.transform.FieldSet

/**
 * 커스텀 ItemReader
 *
 * 커스텀 ItemReader를 만든 이유는 ExitStatus가 Reader의 상태에 묶여있기 때문?
 */
data class TransactionReader(
    var fieldSetReader: ItemStreamReader<FieldSet>,
    var recordCount: Int = 0,
    var expectedRecordCount: Int = 0,
): ItemStreamReader<Transaction> {
    /**
     *
     */
    override fun open(executionContext: ExecutionContext) {
        this.fieldSetReader.open(executionContext)
    }

    override fun update(executionContext: ExecutionContext) {
        this.fieldSetReader.update(executionContext)
    }

    override fun close() {
        this.fieldSetReader.close()
    }

    /**
     *
     */
    override fun read(): Transaction? = process(fieldSetReader.read())

    private fun process(fieldSet: FieldSet?): Transaction? {
        var result: Transaction? = null

        if (fieldSet != null) {
            if (fieldSet.fieldCount > 1) {
                result = Transaction(
                    fieldSet.readString(0),
                    fieldSet.readDate(1, "yyyy-MM-DD HH:mm:ss"),
                    fieldSet.readDouble(2)
                )
                recordCount++
            } else expectedRecordCount = fieldSet.readInt(0)
        }
        return result
    }

    /**
     * 실제 읽어들인 레코드 수와 예상한 레코드 수가 일치하면 처리를 계속하고, 아니라면 Stop한다.
     */
    @AfterStep
    fun afterStep(execution: StepExecution): ExitStatus {
        return if (recordCount == expectedRecordCount) {
            execution.exitStatus
        } else {
            ExitStatus.STOPPED
        }
    }
}
