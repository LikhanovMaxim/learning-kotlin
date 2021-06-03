import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*
import org.jetbrains.kotlin.konan.target.*

plugins {
    kotlin("multiplatform") //version "1.4.21"
    kotlin("plugin.serialization") version "1.4.21"
    id("com.epam.drill.cross-compilation") version ("0.17.0")
    id("com.epam.drill.gradle.plugin.kni") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
//    id("me.filippov.gradle.jvm.wrapper") version "0.9.2"
//    application
    distribution
}
val scriptUrl: String by extra
val kotlinVersion = "1.4.21"


val drillJvmApiLibVersion: String by extra
val ktorUtilVersion = "1.3.2"
val kniVersion = "0.3.0"
val kniOutputDir = "src/kni/kotlin"

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    gradlePluginPortal()
    apply(from = "$scriptUrl/maven-repo.gradle.kts")
    maven(url = "https://oss.jfrog.org/oss-release-local")
//    apply(from = "https://raw.githubusercontent.com/Drill4J/build-scripts/master/maven-repo.gradle.kts")
}

val libName = "my-native"
//application {
//    mainClassName = "com.HiKt"
//}
//application {
//    mainClassName = "com.HelloKt" //TODO package
//}

kotlin {
    val platforms = setOf(
//        mingwX64("native") {
//            binaries {
//                executable()
//            }
//        } ,
        mingwX64 { binaries.all { linkerOpts("-lpsapi", "-lwsock32", "-lws2_32", "-lmswsock") } },
//        ,
//        linuxX64("native") {
//            binaries {
//                executable()
//            }
//        }
//    ,macosX64()
        linuxX64()
    )
    platforms.forEach { target ->
        target.binaries { sharedLib(libName, setOf(DEBUG)) }
        target.compilations["test"].cinterops.create("testStubs")
    }
    sourceSets {
        listOf(
            "kotlin.ExperimentalStdlibApi",
            "kotlin.ExperimentalUnsignedTypes",
            "kotlin.time.ExperimentalTime",
            "kotlinx.serialization.ExperimentalSerializationApi",
//            "kotlinx.coroutines.ExperimentalCoroutinesApi",
            "kotlinx.serialization.InternalSerializationApi"
//            "io.ktor.utils.io.core.ExperimentalIoApi"
        ).let { annotations ->
            all { annotations.forEach(languageSettings::useExperimentalAnnotation) }
        }
        commonMain {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-properties:1.0.1")
//                implementation("io.ktor:ktor-utils-jvm:$ktorUtilVersion")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter")
            }
        }
    }
    crossCompilation {
        common {
            defaultSourceSet {
                dependsOn(sourceSets.commonMain.get())
                dependencies {
//                    implementation("io.ktor:ktor-utils-native:$ktorUtilVersion")
                    implementation("com.epam.drill:jvmapi:$drillJvmApiLibVersion")
                    implementation("com.epam.drill.kni:runtime:$kniVersion")
                }
            }
        }
    }
    kni {
        jvmTargets = sequenceOf(jvm())
        additionalJavaClasses = sequenceOf()
        srcDir = kniOutputDir
        //def file for what?
    }

    jvm {
        val main by compilations
        main.defaultSourceSet {
            dependencies {
                compileOnly(kotlin("stdlib-jdk8"))
                implementation("com.epam.drill.kni:runtime:$kniVersion")
//                compileOnly("org.jetbrains.kotlinx:atomicfu:$atomicFuVersion")
//                implementation("io.ktor:ktor-utils-jvm:$ktorUtilVersion")
//                implementation("com.soywiz.korlibs.klock:klock-jvm:$klockVersion")
//                implementation("com.epam.drill.kni:runtime-jvm:$kniVersion")
            }
        }
    }

    val runNative by tasks.creating(JavaExec::class){
        main = "com.HiKt"
        group = "application"
        dependsOn(jvm().compilations.map { it.compileAllTaskName })

        //todo???
//        dependsOn(platforms.forEach {
//            it.compilations.map { it.compileAllTaskName }
//        })
//        dependsOn(platforms.forEach {
//            it.binaries.map { it.linkTaskName }
//        })
        val first = platforms.first()
        println("platform=$first")
        dependsOn(first.compilations.map { it.compileAllTaskName })
        dependsOn(first.binaries.map { it.linkTaskName })
        doFirst {
            classpath(
                jvm().compilations["main"].output.allOutputs.files,
                configurations["jvmRuntimeClasspath"]
            )

            ///disable app icon on macOS
//            systemProperty("java.awt.headless", "true")
//            systemProperty("java.library.path", first.binaries.findSharedLib("debug")!!.outputDirectory)
        }
    }
}

tasks{
    val generateNativeClasses by getting {}
    withType<KotlinNativeCompile> {
        dependsOn(generateNativeClasses)
    }
}


//tasks.wrapper {
//    distributionType = Wrapper.DistributionType.ALL
//}
//
//jvmWrapper {
//    linuxJvmUrl = "https://d3pxv6yz143wms.cloudfront.net/8.232.09.1/amazon-corretto-8.232.09.1-linux-x64.tar.gz"
//    macJvmUrl = "https://d3pxv6yz143wms.cloudfront.net/8.232.09.1/amazon-corretto-8.232.09.1-macosx-x64.tar.gz"
//    windowsJvmUrl ="https://d3pxv6yz143wms.cloudfront.net/8.232.09.1/amazon-corretto-8.232.09.1-windows-x86-jdk.zip"
//}
//
//tasks.withType<Wrapper> {
//    gradleVersion = "6.7.1"
//    distributionType = Wrapper.DistributionType.BIN
//}

val runtimeJar by tasks.registering(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
    mergeServiceFiles()
    isZip64 = true
    archiveFileName.set("native-kni.jar")
    val main by kotlin.jvm().compilations
    from(
        provider { main.output },
        provider { main.runtimeDependencyFiles }
    )
    relocate("kotlin", "kruntime")
}

afterEvaluate {
    val availableTargets =
        kotlin.targets.filterIsInstance<KotlinNativeTarget>().filter { HostManager().isEnabled(it.konanTarget) }

//    println(availableTargets)
    distributions {
        availableTargets.forEach {
//            println(it)
            val name = it.name
            create(name) {
                distributionBaseName.set(name)
                contents {
                    from(runtimeJar)
                    from(tasks.named("link${libName.capitalize()}DebugShared${name.capitalize()}")) {
                        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    }
                }
            }
        }
    }
}

/**
How to run/use compiled lib?
 * fixme runNative
 * Exception in thread "main" java.lang.UnsatisfiedLinkError: com.WebContainerSource.webAppStarted(Ljava/lang/String;)V
at com.WebContainerSource.webAppStarted(Native Method)
at com.WebContainerSource.fillWebAppSource(WebContainerSource.kt:11)
 */
