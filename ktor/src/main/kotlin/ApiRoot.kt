import io.ktor.locations.*

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/{prefix}")
class ApiRoot(val prefix: String = "api") {
    @Location("/version")
    data class Version(val parent: ApiRoot)

    @Location("/{agentId}/parameters")
    data class AgentParameters(val parent: ApiRoot, val agentId: String)

}
