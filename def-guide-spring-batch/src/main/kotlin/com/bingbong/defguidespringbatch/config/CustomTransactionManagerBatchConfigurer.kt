package com.bingbong.defguidespringbatch.config

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.transaction.PlatformTransactionManager

class CustomTransactionManagerBatchConfigurer(
    @Autowired @Qualifier("batchTransactionManager")
    private val transactionManager: PlatformTransactionManager,
): DefaultBatchConfigurer() {
    override fun getTransactionManager(): PlatformTransactionManager {
        return this.transactionManager
    }
}