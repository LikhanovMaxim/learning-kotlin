plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    id("org.jetbrains.kotlin.plugin.jpa") version "1.5.31"
    application
}

application {
    mainClassName = "com.HelloH2Kt" //TODO package
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

// https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:1.7.32")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")

// https://mvnrepository.com/artifact/org.hibernate/hibernate-core
    implementation("org.hibernate:hibernate-core:5.6.0.Final")
// https://mvnrepository.com/artifact/org.hibernate/hibernate-testing
    implementation("org.hibernate:hibernate-testing:5.6.0.Final")
// https://mvnrepository.com/artifact/com.h2database/h2
    implementation("com.h2database:h2:1.4.200")

    testImplementation(project(":common-util"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
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
