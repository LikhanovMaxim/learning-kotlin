package com

data class Context(
    val build: Build = Build("", "", "")
)

data class Build(
    val version: String,
    val parentVersion: String,
    val data: String
)

fun main() {
    println("lol")

    val build = Build("1", "0", "dataasdasd")

    val build2 = updateBuild(build){
        copy(version = "4444")
    }
    println(build2)
}

val context: Context= Context()

private fun updateBuild(
    build: Build,
    updater: Build.() -> Build
): Build {
    val copy = context.copy(
        build = build.updater()
    )
    return copy.build
}
