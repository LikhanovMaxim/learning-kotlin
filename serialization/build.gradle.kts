plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("kotlinx-atomicfu")
    application
}
//apply plugin: 'kotlinx-serialization'
application {
    mainClassName = "com.HelloKt" //TODO package
}

val serializationVersion:String by project
dependencies {
    implementation(kotlin("stdlib-jdk8"))

    //provided by drill runtime
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-cbor:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    //provided by admin
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable-jvm")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.jetbrains.kotlinx:atomicfu")
}

