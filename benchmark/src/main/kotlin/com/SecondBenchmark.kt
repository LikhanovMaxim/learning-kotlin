package com

import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

/**
 * Fork vs Measurement & Warmup?
 */

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
//@Measurement(iterations = 3, time = 4, timeUnit = TimeUnit.MILLISECONDS)
class SecondBenchmark {
    private var data = 0.0

    @Setup
    fun setUp() {
        data = 3.0
    }

    @TearDown
    fun tearDown() {
        data = 0.0
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)
    @Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.MILLISECONDS)
    fun cosBenchmark() {
        data += data
        println("data $data")
        Thread.sleep(500)
    }

    /**
     *
     * BenchmarkMode: Throughput , AverageTime, SampleTime, and SingleShotTime.
     */
    @Benchmark
    @Fork(value = 3, warmups = 0)
    @BenchmarkMode(Mode.AverageTime)
    @Warmup(iterations = 0)//todo
    @Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.MILLISECONDS)
    fun testCalcSec() {
//        Thread.sleep(1000)
        val usualClass = UsualClass()
        usualClass.manyForInFor(1)
    }

}
