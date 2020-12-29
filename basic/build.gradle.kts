plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    id("com.github.johnrengelman.shadow")
    application
}

application {
    mainClassName = "com.HelloKt" //TODO package
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
}
tasks {
    test {
        useJUnitPlatform()
    }
}

val runtimeJar by tasks.registering(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
    mergeServiceFiles()
    isZip64 = true
    archiveFileName.set("drillRuntime.jar")
//    val main by kotlin.jvm().compilations
//    from(
//        provider { main.output },
//        provider { main.runtimeDependencyFiles }
//    )
    manifest
//    relocate("kotlin", "kruntime")
}
