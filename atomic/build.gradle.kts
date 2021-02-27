plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
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

