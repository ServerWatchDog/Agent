package i.agent.mod

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SystemModTest {

    @Test
    fun execute() {
        for (support in SystemMod.supports) {
            println("support: $support \t\t\t ${SystemMod.execute(support)}")
        }
    }
}
