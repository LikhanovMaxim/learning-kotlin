plugins {
    id("org.jetbrains.kotlin.multiplatform")
//    id("com.epam.drill.cross-compilation") version ("0.16.2")
    kotlin("plugin.serialization") version "1.4.21"
}
repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    gradlePluginPortal()
    maven(url = "https://oss.jfrog.org/oss-release-local")
//    apply(from = "https://raw.githubusercontent.com/Drill4J/build-scripts/master/maven-repo.gradle.kts")
}

val kotlinVersion = "1.4.21"
val ktorUtilVersion = "1.3.2"

kotlin {
    setOf(
        mingwX64("native") {
            binaries {
                executable()
            }
        }
//        ,
//        linuxX64("native") {
//            binaries {
//                executable()
//            }
//        }
//    ,macosX64()
    )
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-hocon:1.0.1")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.0.1")
//                implementation("com.charleskorn.kaml:kaml:0.26.0")
//                YAML:
                implementation("net.mamoe.yamlkt:yamlkt:0.7.5")
                implementation("io.ktor:ktor-utils-jvm:$ktorUtilVersion")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter")
//                implementation("org.jetbrains.kotlin:kotlin-test-common")
//                implementation("org.jetbrains.kotlin:kotlin-test-annotations-common")
            }
        }
    }
//    jvm {
//        val main by compilations
//        main.defaultSourceSet {
//            dependencies {
//                compileOnly(kotlin("stdlib"))
////                compileOnly("org.jetbrains.kotlinx:atomicfu:$atomicFuVersion")
//                implementation("io.ktor:ktor-utils-jvm:$ktorUtilVersion")
////                implementation("com.soywiz.korlibs.klock:klock-jvm:$klockVersion")
////                implementation("com.epam.drill.kni:runtime-jvm:$kniVersion")
//            }
//        }
//    }
//    crossCompilation {
//        common {
//            defaultSourceSet {
//                dependsOn(sourceSets.commonMain.get())
//                dependencies {
//                    implementation("io.ktor:ktor-utils-native:$ktorUtilVersion")
//                }
//            }
//        }
//    }
}



tasks.withType<Wrapper> {
    gradleVersion = "6.7.1"
    distributionType = Wrapper.DistributionType.BIN
}
