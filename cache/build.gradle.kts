plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    application
}

application {
    mainClassName = "com.HelloKt" //TODO package
    applicationDefaultJvmArgs = listOf(
        "-Xmx1g",
        "-XX:MaxDirectMemorySize=10G" //it is param for mapDb if it uses memoryDirectDB
        // todo what does size choose? what is side affect if put a lot of gb? where it stores, on disk?
    )
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")
// https://mvnrepository.com/artifact/net.sf.ehcache/ehcache
//    implementation("net.sf.ehcache:ehcache:2.10.6")
    implementation("org.mapdb:mapdb:3.0.8")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
}
tasks {
    test {
        useJUnitPlatform()
    }
}
