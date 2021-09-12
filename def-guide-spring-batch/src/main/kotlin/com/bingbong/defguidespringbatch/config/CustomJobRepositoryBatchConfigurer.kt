package com.bingbong.defguidespringbatch.config

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import javax.sql.DataSource

class CustomJobRepositoryBatchConfigurer(
    @Autowired @Qualifier("repositoryDataSource")
    private val dataSource: DataSource,
): DefaultBatchConfigurer() {
    override fun createJobRepository(): JobRepository {
        val factoryBean = JobRepositoryFactoryBean()
        factoryBean.setDataSource(this.dataSource)
        factoryBean.setTablePrefix("FOO_")
        factoryBean.setIsolationLevelForCreate("ISOLATION_REPEATABLE_READ")
        factoryBean.afterPropertiesSet()
        return factoryBean.`object`
    }
}