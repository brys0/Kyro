package brys.org.dev.Kyro

import kotlin.system.exitProcess

object KyroShutDownHandler {
    fun exit(EXITCODE: Int, Planned: Boolean, Reason: String) {
        val success = 0
        val failure = 1
        val UNKNOWN = 2000

        if (EXITCODE == failure) {
            println("Exiting with failure. Planned? $Planned.")
            exitProcess(1)
        }
        if (EXITCODE == success) {
            println("Exiting successfully. Planned? $Planned.")
            exitProcess(0)
        }
        if (EXITCODE == UNKNOWN) {
            println("Exiting as UNKNOWN (2000). Planned? $Planned. Exit message: $Reason.")
            exitProcess(2000)
        }
    }
    fun exit(EXITCODE: Int) {

    }
}