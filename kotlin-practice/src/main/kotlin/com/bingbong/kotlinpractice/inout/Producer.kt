package com.bingbong.kotlinpractice.inout

// in, out 키워드는 함수의 인자, 리턴 타입에 대한 책임 분리!
interface Producer<T> {
    fun produce(): T
    fun produce(t : T)
}

interface ProducerOut<out T> {
    fun produce(): T // 리턴 타입이 T이므로 가능
//    fun produce(t : T): T // T가 매개변수에 적용되고 있으므 컴파일 에러
}

interface ProducerIn<in T> {
    fun produce(t : T) // 인자 타입이 T이므로 가능
//    fun produce(): T // 리턴 타입이 T로 컴파일 에러
}


