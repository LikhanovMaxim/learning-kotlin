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
}

val kotlinVersion = "1.4.21"
val ktorUtilVersion = "1.3.2"

kotlin {
    setOf(
        mingwX64(),
        linuxX64()
    )
    sourceSets {
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.0.1")

                implementation("io.ktor:ktor-utils-jvm:$ktorUtilVersion")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter")
            }
        }
    }
//    jvm {
//        val main by compilations
//        main.defaultSourceSet {
//            dependencies {
//                compileOnly(kotlin("stdlib"))
//                implementation("io.ktor:ktor-utils-jvm:$ktorUtilVersion")
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
