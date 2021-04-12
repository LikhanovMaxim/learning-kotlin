rootProject.name = "kotlin_learning"

apply(from = "plugins.settings.gradle.kts")

include(":atomic")
include(":serialization")
include(":basic")
include(":ktor")
include(":ktor-websocket")
include(":native")
include(":native-modules")
include(":cache")
include(":coroutines")
include(":reflection")
include(":generate-sources")
include(":benchmark")
include(":common-util")
