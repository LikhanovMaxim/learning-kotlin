package com

import io.ktor.locations.*
import kotlin.reflect.*
import kotlin.reflect.full.*

@KtorExperimentalLocationsAPI
fun main() {
    println("hello reflection")
//    experiments()
}

@KtorExperimentalLocationsAPI
val locationRouteService = LocationAttributeRouteService()

@KtorExperimentalLocationsAPI
fun findAllRoutesPath(
    kClassRoute: KClass<out Any>,
    allRoutesPath: List<String> = listOf(), //todo fix mutable collection
    parentPath: String = "",
): List<String> {
    val flatten: List<String> = kClassRoute.nestedClasses.mapNotNull { kClass: KClass<*> ->
        locationRouteService.findRoute(kClass)?.let {
            val newPath = parentPath + it
            val list = if (kClass.nestedClasses.any()) {
                findAllRoutesPath(kClass, allRoutesPath, newPath)
            } else emptyList()
            list.plus(newPath)
        }
    }.flatten()
    println(flatten)
    return flatten
}

@KtorExperimentalLocationsAPI
private fun experiments() {
    val locationRouteService = LocationAttributeRouteService()
    val someRoute = SomeRoute::class
    val location = someRoute.findAnnotation<Location>()
    val path2 = locationRouteService.findRoute(someRoute)
    println("path of SomeRoute=$path2")
    val annotations = someRoute.annotations
    annotations.map {
        println(it)
    }
    println(location)

    val r = Routes.FirstNested()
    println(r)
//    val m = MyClass()
    val myClass = MyClass::class
    val suppress = myClass.findAnnotation<Suppress>()
    val sourceData = myClass.findAnnotation<SourceData>()
    println(sourceData?.author)
    println(suppress?.names)
}
