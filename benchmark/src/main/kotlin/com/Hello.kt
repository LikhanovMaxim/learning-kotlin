package com


import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.*
import kotlin.math.cos
import kotlin.math.sqrt
import kotlin.time.measureTimedValue

fun main() {
    println("benchmark")
    val h = Hello()
    println(h.sfa(213))
    val usualClass = UsualClass()
    measureTimedValue {
        usualClass.manyForInFor(1)
    }.also { println("duration iteration: ${it.duration.inSeconds} s = ${it.duration.inMilliseconds} ms") }
}


class UsualClass {
    fun manyForInFor(start: Long): Long {
        var sum = start
        val random = Random()
        val firstRange = 3
        val toMin = 8
        val toMax = 3
        for (i10 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
            sum += i10.toLong()
            for (i9 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                sum += i9.toLong()
                for (i8 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                    sum += i8.toLong()
                    for (i7 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                        sum += i7.toLong()
                        for (i6 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                            sum += i6.toLong()
                            for (i5 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                                sum += i5.toLong()
                                for (i4 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                                    sum += i4.toLong()
                                    for (i3 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                                        sum += i3.toLong()
                                        for (i2 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                                            sum += i2.toLong()
                                            for (i1 in random.nextInt(firstRange)..random.nextInt(toMax) + toMin) {
                                                sum += i1.toLong()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return sum
    }
}

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
class Hello {

//    @Benchmark
    public fun sfa(fa: Int): Int {
        return fa * fa
    }
}

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
@Measurement(iterations = 1, time = 1, timeUnit = TimeUnit.MILLISECONDS)
class KtsTestBenchmark {
    private var data = 0.0

    @Setup
    fun setUp() {
        data = 3.0
    }

    @Benchmark
    fun sqrtBenchmark(): Double {
        return sqrt(data)
    }

    @Benchmark
    fun cosBenchmark(): Double {
        return cos(data)
    }

    @Benchmark
    fun testCalc() {
//        Thread.sleep(1000)
//        val usualClass = UsualClass()
//        usualClass.manyForInFor(1)
    }
}
