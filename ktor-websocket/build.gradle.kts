plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") //version kotlinVersion
    id("kotlinx-atomicfu")
    application
}

application {
    mainClassName = "TestKt" //TODO package
}

val ktorVersion = "1.5.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-websockets:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:2.0.6")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
}
//todo create docker image
tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>{
        kotlinOptions{
            jvmTarget = "1.8"
        }
    }
    test {
        useJUnitPlatform()
    }
}

