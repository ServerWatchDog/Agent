package i.agent.mod

interface IMod {
    val supports: Set<String>

    fun execute(key: String): String
}
