package com.example.dagger2test

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.util.concurrent.TimeUnit

class ExampleUnitTestRxJava {
    @Test
    fun testRxJava() {
//        testObservableCreate()
//        testObservableJust()
//        testSingle()
//        testMaybe()
//        testCompletable()
//        testHotObservable1()
//        testHotObservable2()
        testDisposable()
    }

    private fun testObservableCreate() {
        val source: Observable<String> = Observable.create {
            // onNext()로 데이터를 순차적으로 발행
            it.onNext("hi")
            it.onNext("hello")
            // 데이터 발행이 끝났다면 반드시 onComplete() 호출
            it.onComplete()
        }

        // Consumer를 통해 구독
        source.subscribe(System.out::println)
    }

    private fun testObservableJust() {
        val source: Observable<String> = Observable.just("hello","hi")

        // 아이템 발행 없음
        val source2 = Observable.empty<String>()

        source2.subscribe(System.out::println)
        source.subscribe(System.out::println)
    }

    private fun testSingle() {
        Single.just("hello")
            .subscribe(System.out::println)

        // onSuccess()가 onNext(), onComplete()를 대체
        Single.create<String> { it.onSuccess("hi") }
            .subscribe(System.out::println)
    }

    private fun testMaybe() {
        Maybe.create<Int> {
            it.onSuccess(100)
            it.onComplete() // 무시됨
        }
            .doOnSuccess { println("doOnSuccess") } // onSuccess() 호출시 호출됨
            .doOnComplete { println("doOnComplete") } // onComplete() 호출시 호출됨
            .subscribe(System.out::println)

        Maybe.create<Int> {
            it.onComplete()
            it.onSuccess(1) // 무시됨
        }
            .doOnSuccess { println("doOnSuccess") }
            .doOnComplete { println("doOnComplete") }
            .subscribe(System.out::println)
    }

    private fun testCompletable() {
        Completable.create {
            println("run")
            it.onComplete()
        }.subscribe()
    }

    private fun testHotObservable1() {
        // publish & connect

        // interval(): 특정 시간별로 연속된 정수형을 배출하는 Observable 생성
        val src = Observable.interval(1, TimeUnit.SECONDS)
            .publish()
        src.connect()
        src.subscribe { println("#1: $it") }
        Thread.sleep(3000)
        src.subscribe { println("#2: $it") }
        Thread.sleep(3000)
    }

    private fun testHotObservable2() {
        // autoConnect
    }

    private fun testDisposable() {
        // dispose
        // CompositeDisposable
    }
}