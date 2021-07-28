plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    application
}

application {
    mainClassName = "com.HelloKt" //TODO package
}
val xodusVersion = "1.3.232"
val xodusDnqVersion = "1.4.480"
val kotlinVersion = "1.4.30"
val cacheMapDB: String = "3.0.8"//by extra

val serializationVersion: String by project
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")
//    implementation("org.jetbrains.xodus:xodus-openAPI:$xodusVersion")
    implementation("org.jetbrains.xodus:xodus-entity-store:$xodusVersion")
//    implementation("org.jetbrains.xodus:dnq:$xodusDnqVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")//todo remove?
    testImplementation(project(":common-util"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
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
}
