plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    application
}

application {
    mainClassName = "com.HelloKt" //TODO package
}
val ktorVersion = "1.5.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.20")
// https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
//    runtimeOnly group: 'org.jetbrains.kotlin', name: 'kotlin-reflect', version: '1.4.20'

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
}
tasks {
    test {
        useJUnitPlatform()
    }
}
