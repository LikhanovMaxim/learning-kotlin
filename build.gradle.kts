import org.jetbrains.kotlin.konan.target.*

plugins {
    kotlin("jvm") version "1.3.60"
    kotlin("multiplatform") version "1.3.60" apply(false)
    application
    java
}

repositories {
    jcenter()
    mavenLocal()
    maven(url = "http://oss.jfrog.org/oss-release-local")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.eclipse.jetty:jetty-servlet:9.4.6.v20170531")
    implementation("javax.servlet:javax.servlet-api:3.1.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:1.7.8")
}


application {
    mainClassName = "com.HelloKt" //TODO package
}

