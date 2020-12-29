plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    id("com.github.johnrengelman.shadow")
    application
}

application {//TODO package
//    mainClassName = "com.HelloKt"
//    mainClassName = "com.test2code.AtomicSinglethonKt"
//    mainClassName = "com.test2code.SimpleClassKt"
    mainClassName = "com.test2code.SameAsTest2CodeKt"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    //provided by drill runtime
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor")

    //provided by admin
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")
    implementation("org.jetbrains.kotlinx:atomicfu")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
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
