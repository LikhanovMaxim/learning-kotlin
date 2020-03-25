import org.jetbrains.kotlin.konan.target.*

plugins {
    kotlin("jvm") version "1.3.60"
    kotlin("multiplatform") version "1.3.60" apply(false)
    application
    java
//    kotlin("plugin.serialization")
//    id("kotlinx-atomicfu")
//    id("org.jetbrains.kotlinx:atomicfu-gradle-plugin") version "0.14.2"
//    id("kotlinx-atomicfu")

}

repositories {
    jcenter()
    mavenLocal()
//    maven(url = "http://oss.jfrog.org/oss-release-local")
    gradlePluginPortal()
    maven(url = "https://dl.bintray.com/kotlin/kotlinx/")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.eclipse.jetty:jetty-servlet:9.4.6.v20170531")
    implementation("javax.servlet:javax.servlet-api:3.1.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:1.7.8")

//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor")
    implementation("org.jetbrains.kotlinx:atomicfu:0.14.2")
    implementation("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.14.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.1")
}


application {
    mainClassName = "com.HelloKt" //TODO package
}

