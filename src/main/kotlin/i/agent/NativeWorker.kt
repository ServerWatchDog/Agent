package i.agent

import java.io.File
import java.nio.charset.Charset

object NativeWorker {
    private val runtime = Runtime.getRuntime()
    fun execute(vararg command: String): String {
        val executeFile = File.createTempFile("tmp", "sh")
        val printWriter = executeFile.printWriter()
        printWriter.println("#!/bin/bash")
        for (s in command) {
            printWriter.print(s)
            printWriter.print(" ")
        }
        printWriter.close()
        return runtime.exec(arrayOf("bash", executeFile.absolutePath)).inputStream.readAllBytes()
            .toString(Charset.defaultCharset()).trim().apply { executeFile.delete() }
    }

    fun executeScript(path: String, vararg args: String): String {
        val executeFile = File.createTempFile("tmp", "sh")
        executeFile
            .writeText(javaClass.getResourceAsStream("/script$path").readAllBytes().toString(Charsets.UTF_8))
        return execute("bash", executeFile.absolutePath, *args).apply { executeFile.delete() }
    }
}
