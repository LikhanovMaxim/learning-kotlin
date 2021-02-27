package com

data class Context(
    val build: Build = Build("", "", "")
)

data class Build(
    val version: String,
    val parentVersion: String,
    val data: String
)

val context: Context = Context()

 fun updateBuild(
    build: Build,
    updater: Build.() -> Build
): Build {
    val copy = context.copy(
        build = build.updater()
    )
    return copy.build
}
