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
// https://mvnrepository.com/artifact/net.sf.ehcache/ehcache
//    implementation group: 'net.sf.ehcache', name: 'ehcache', version: '2.10.6'

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
}
tasks {
    test {
        useJUnitPlatform()
    }
}
