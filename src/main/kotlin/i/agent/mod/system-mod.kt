package i.agent.mod

import i.agent.NativeWorker

object SystemMod : IMod {
    override val supports = setOf(
        "system_name",
        "system_cpu",
        "system_memory",
        "system_disk",
        "system_network",
    )

    override fun execute(key: String): String {
        return when (key) {
            "system_name" -> NativeWorker.execute("uname", "-n")
            "system_cpu" -> NativeWorker.execute("cat /proc/cpuinfo  | grep \"model name\" | head -n 1 | cut -d \":\" -f 2")
            "system_memory" -> NativeWorker.execute("LANG=c free --bytes | grep Mem | head -n 1 | awk '{print \$2}'")
            "system_disk" -> NativeWorker.execute("LANG=c df /  | grep \"/\" |awk '{print \$2}' | head -n 1")
            "system_network" -> NativeWorker.executeScript("/network-info.sh")
            else -> ""
        }
    }
}
