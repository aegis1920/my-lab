package com.bingbong.defguidespringbatch.config

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import javax.sql.DataSource

class CustomJobExplorerBatchConfigurer(
    @Autowired @Qualifier("batchJobExplorer")
    private val dataSource: DataSource,
): DefaultBatchConfigurer(dataSource) {

    override fun setDataSource(dataSource: DataSource) {
    }

    override fun createJobExplorer(): JobExplorer {
        val factoryBean = JobExplorerFactoryBean()
        factoryBean.setDataSource(this.dataSource)
        factoryBean.setTablePrefix("FOO_")
        factoryBean.afterPropertiesSet()
        return factoryBean.`object`
    }
}