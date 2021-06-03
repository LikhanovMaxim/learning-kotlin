import org.jetbrains.kotlin.konan.target.*

plugins {
    kotlin("jvm")
    application
}

application {
    mainClassName = "com.HelloKt"
}

repositories {
    mavenLocal()
    maven("https://dl.bintray.com/drill/drill4j")
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":native-kni"))
}

val target = HostManager.host.presetName

tasks {
    test {
        useJUnitPlatform()
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlin.ExperimentalStdlibApi",
            "-Xuse-experimental=kotlin.time.ExperimentalTime"
//            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
//            "-Xuse-experimental=kotlinx.coroutines.FlowPreview",
//            "-Xuse-experimental=kotlinx.coroutines.InternalCoroutinesApi",
//            "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi"
        )
    }
    (run) {
//        val installTaskName = "install${target.capitalize()}Dist"
        val installTaskName = "installMingwX64Dist"
        dependsOn(find?.tasks?.named(installTaskName))
    }
}

val find: Project? = rootProject.allprojects.find { it.name == "native-kni" }
//println(find)
