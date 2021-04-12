@file:Suppress("UnstableApiUsage")

import kotlinx.benchmark.gradle.*
import org.jetbrains.kotlin.allopen.gradle.*

plugins {
    kotlin("jvm")
    kotlin("plugin.allopen") version "1.4.0"
    id("org.jetbrains.kotlinx.benchmark") version "0.3.0"
    application
}

application {
    mainClassName = "com.HelloKt" //TODO package
}

//sourceSets.all {
//    java.setSrcDirs(listOf("$name/src"))
//    resources.setSrcDirs(listOf("$name/resources"))
//}


configure<AllOpenExtension> {
    annotation("org.openjdk.jmh.annotations.State")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")
//    implementation("org.jetbrains.kotlinx:kotlinx.benchmark.runtime-jvm:0.2.0-dev-20")
//    implementation("org.jetbrains.kotlinx:kotlinx.benchmark.runtime-jvm:0.3.0")
//    implementation(project(":kotlinx-benchmark-runtime"))


    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
kotlin {
    sourceSets.main {
        dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.3.0")
        }
    }
}
tasks {
    test {
        useJUnitPlatform()
    }
}

benchmark {
    configurations {
        named("main") {
            iterationTime = 5
            iterationTimeUnit = "ms"
        }
//        named("mySmoke"){
//            iterationTime = 5
//            iterationTimeUnit = "ms"
//        }
    }
    targets {
        register("main") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
        register("test") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
    }
}
