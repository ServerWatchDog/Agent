package i.agent

import i.agent.mod.IMod
import i.agent.mod.InfoMod
import i.agent.mod.SystemMod
import i.agent.model.AgentInfoResultView
import i.agent.model.AgentPushResultView
import i.agent.model.AgentPushView
import i.agent.utils.HttpUtils
import org.d7z.logger4k.core.utils.getLogger
import java.util.concurrent.TimeUnit

/**
 * 代理程序
 * @constructor
 */

class ServerAgent(serverAddress: String, serverToken: String) : Runnable {
    private val logger = getLogger()
    private val http = HttpUtils(serverAddress, serverToken)
    private val mods = listOf(SystemMod, InfoMod)
    private val enableModule = HashMap<String, IMod>()
    private val refreshTime: Long

    init {
        logger.info("初始化 agent,服务器地址为 {}.", serverAddress)
        logger.info("拉取服务器配置中 ...")
        val agentInfo = http.get("/agent/info", AgentInfoResultView::class).get()
        refreshTime = agentInfo.refreshTime
        logger.info("配置拉取成功，启用模块 {}", agentInfo.pushType)
        for (mod in mods) {
            for (support in mod.supports) {
                if (agentInfo.pushType.contains(support)) {
                    enableModule[support] = mod
                    logger.info("启用模块 {} ,实现对象为 {}.", support, mod::class.java)
                }
            }
        }
        logger.info("代理初始化完成，开始采集，采集间隔为 {} 秒.", refreshTime)
    }

    override fun run() {
        while (true) {
            TimeUnit.SECONDS.sleep(refreshTime)

            if (http.post(
                    "/agent/push", AgentPushResultView::class,
                    AgentPushView(enableModule.map { Pair(it.key, it.value.execute(it.key)) }.toMap())
                ).get().status != 0
            ) {
                throw java.lang.RuntimeException("触发配置更新！")
            }
        }
    }
}
