package com.bingbong.defguidespringbatch.validators

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator
import org.springframework.util.StringUtils

// 직접 Validator를 구현한 것
class ParameterValidator : JobParametersValidator {
    override fun validate(parameters: JobParameters?) {
        val fileName = parameters?.getString("fileName")

        if (!StringUtils.hasText(fileName)) {
            throw JobParametersInvalidException("fileName parameters is missing")
        } else if(!StringUtils.endsWithIgnoreCase(fileName, "csv")) {
            throw JobParametersInvalidException("fileName 마지막이 csv가 아닙니당")
        }
    }
}