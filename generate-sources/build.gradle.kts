plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    application
}

application {
    mainClassName = "com.HelloKt" //TODO package
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")
    implementation("com.squareup:kotlinpoet:1.8.0")
    implementation("com.squareup:javapoet:1.13.0")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
}
tasks {
    test {
        useJUnitPlatform()
    }
}
