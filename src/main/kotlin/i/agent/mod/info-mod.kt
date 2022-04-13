package i.agent.mod

import i.agent.NativeWorker

object InfoMod : IMod {
    override val supports = setOf(
        "status_cpu_usage",
        "status_disk",
        "status_memory",
        "status_network_speed",
        "status_push_time",
        "status_uptime"
    )

    override fun execute(key: String): String {
        return when (key) {
            "status_cpu_usage" -> NativeWorker.execute("LANG=c expr 100 - \$(top -b -n 1 | grep Cpu | awk '{print \$8}'|cut -f 1 -d \".\")")
            "status_disk" -> NativeWorker.execute("LANG=c df /  | grep \"/\" |awk '{print \$3}' | head -n 1")
            "status_memory" -> NativeWorker.execute("LANG=c free --bytes | grep Mem | head -n 1 | awk '{print \$6}'")
            "status_network_speed" -> NativeWorker.executeScript("/network-speed.sh")
            "status_push_time" -> NativeWorker.execute("LANG=c date \"+%Y/%m/%d %H:%M:%S\"")
            "status_uptime" -> NativeWorker.execute("LANG=c date -d \"`cut -f1 -d. /proc/uptime` seconds ago\" \"+%Y/%m/%d %H:%M:%S\"")
            else -> ""
        }
    }
}
