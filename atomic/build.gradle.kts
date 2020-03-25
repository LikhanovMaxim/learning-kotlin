plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    id("com.github.johnrengelman.shadow")
    application
}

val jarDeps by configurations.creating
configurations.implementation {
    extendsFrom(jarDeps)
}

application {
    mainClassName = "com.HelloKt" //TODO package
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    //plugin dependencies
//    jarDeps(project(":common-part")) { isTransitive = false }
    jarDeps("org.jacoco:org.jacoco.core")

    //provided by drill runtime
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor")

    //provided by admin
    //TODO create a platform for admin dependencies
    implementation("org.jetbrains.xodus:xodus-entity-store")
    implementation("io.ktor:ktor-locations")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
}

