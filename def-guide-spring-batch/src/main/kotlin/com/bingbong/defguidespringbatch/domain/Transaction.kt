package com.bingbong.defguidespringbatch.domain

import java.util.*

/**
 * Transaction 도메인 (데이터 모델)
 * 거래 정보를 뜻한다
 * AccountNumber와 Transaction는 1:N 관계
 *
 * acoountNumber : 계좌번호
 * amount : 거래량
 */
data class Transaction(
    var accountNumber: String?,
    val timestamp: Date,
    val amount: Double,
)
