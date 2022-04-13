import i.agent.ServerAgent
import org.d7z.logger4k.core.LoggerFactory
import org.d7z.logger4k.core.utils.getLogger
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.isEmpty() || args.size == 1) {
        System.err.println("请指定 token 和服务器地址")
        exitProcess(-1)
    }
    val serverAddress = args[0]
    val serverToken = args[1]
    val logger = LoggerFactory.getLogger("Application")
    while (true) {
        try {
            ServerAgent(serverAddress, serverToken).run()
        } catch (e: Exception) {
            logger.warnThrowable("agent 发生异常.", e)
        }
        logger.warn("agent 异常退出，重试中 ...")
        Thread.sleep(1000)
    }
}
