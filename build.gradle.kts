plugins {
    base
    distribution
    kotlin("jvm") apply false
}

val atomicFuVersion: String by project
val ktorVersion: String by project

subprojects {
    apply<BasePlugin>()

    repositories {
        mavenLocal()
        maven(url = "https://oss.jfrog.org/artifactory/list/oss-release-local")
        mavenCentral()
        jcenter()
    }

    val constraints = listOf(
        "org.jetbrains.kotlinx:atomicfu:$atomicFuVersion",
        "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0",
        "org.jetbrains.kotlinx:kotlinx-serialization-cbor:0.20.0",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4",
        "org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm:0.3.1",
        "com.epam.drill:kodux-jvm:0.1.6",
        "org.jetbrains.xodus:xodus-entity-store:1.3.91",
        "io.ktor:ktor-locations:$ktorVersion",
        "org.jacoco:org.jacoco.core:0.8.5",
        "org.junit.jupiter:junit-jupiter:5.5.2"
    ).map(dependencies.constraints::create)

    configurations.all {
        dependencyConstraints += constraints
    }

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                allWarningsAsErrors = true
                freeCompilerArgs = listOf(
                    "-Xuse-experimental=kotlin.Experimental",
                    "-Xuse-experimental=kotlin.time.ExperimentalTime"
                )
            }
        }

        clean {
            delete("distr", "work")
        }
    }
}

val pluginConfigJson = file("plugin_config.json")
