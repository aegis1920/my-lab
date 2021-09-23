package com.bingbong.defguidespringbatch.domain

/**
 * AccounSummary 도메인 (데이터 모델)
 * 간단한 계좌를 뜻한다
 *
 * accountNumber : 계좌번호
 * currentBalance : 카드사에서 해당 일에 승인한 총 금액
 */
data class AccountSummary(
    var id: Int = 0,
    val accountNumber: String,
    var currentBalance: Double,
)