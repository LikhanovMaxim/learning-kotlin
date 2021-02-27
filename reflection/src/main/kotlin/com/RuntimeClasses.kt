package com

import io.ktor.locations.*

@OptIn(KtorExperimentalLocationsAPI::class)
@Suppress("unused")
class Routes {

    @Location("/first-scope")
    class FirstNested

    @Location("/second-scope")
    class SecondNested {
        @Location("/second-child")
        class SecondNestedChild(val secondNested: SecondNested) {
            @Location("/leaf")
            class Leaf(val secondNestedChild: SecondNestedChild)
        }
    }
}

@OptIn(KtorExperimentalLocationsAPI::class)
@Suppress("unused")
class RoutesOne {
    @Location("/first-scope")
    class FirstNested
}

@OptIn(KtorExperimentalLocationsAPI::class)
@Suppress("unused")
class RoutesChild {
    @Location("/second-scope")
    class SecondNested {
        @Location("/second-child")
        class SecondNestedChild(val secondNested: SecondNested)
    }
}

@OptIn(KtorExperimentalLocationsAPI::class)
@Suppress("unused")
@Location("/api-bla-bla")
class SomeRoute

@Suppress(
    names = ["unused"]
)
@SourceData(
    author = "Somebody",
    version = 1,
    lastModified = "2018-05-09")
class MyClass {

    fun getHelloString(): String {
        return "Hello World!"
    }
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)//if it is SOURCE you can't take values by reflection
@MustBeDocumented
annotation class SourceData(
    val author: String,
    val version: Int,
    val lastModified: String,
)
