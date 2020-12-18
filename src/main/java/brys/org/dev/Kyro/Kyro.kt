package brys.org.dev.Kyro


import com.jagrosh.jdautilities.command.*
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role
import oshi.SystemInfo
import java.io.File
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess
import oshi.hardware.CentralProcessor.TickType


object Kyro {
   private var KyroLogoFile: File = File("KyroLogo.png")

    val AVATAR = KyroData.KyroLogoFile

    /**
     * Returns a String of the version of this bot
     */
    val VERSION: String? = "1.0.2"




    /**
     * Exits either with success (0) / failure (1) or UNKNOWN (2000)
     * @param EXITCODE The int for the exitcode
     * @param Planned the boolean if this was planned
     * @param Reason The reason for the shutdown
     * @return Reason if any
     */



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

    object KyroData {
        var fileName = "./DataSets/KyroLogo.png"
        var path = Paths.get(fileName)
        var KyroLogoFile: File = path.toFile()
        val AVATAR = KyroData.KyroLogoFile

        fun getAvatar(): File {
            return AVATAR
        }
    }
    }

/**
 * Kyro's built in util module
 * Below are the functions thus far
 * Bugged(),
 * getTime()
 * @param  CommandEvent - Required param by default
 * @since 1.0.2
 * @author Bryson
 */

object  KyroUtils {
    /**
     * ### Returns a void method that sends a message
     * Message:
     * ### <YOUR_MESSAGE>
     * ## Updated Changes
     * *  Custom bug message
     * *  Set emoji for message
     * ### Required For Function
     * - event: CommandEvent
     * - emoji: String
     * - message: String
     * # Exceptions
     * ### If requester has been shutdown
     * - java.util.concurrent.RejectedExecutionException
     * ### If the bot is missing the Permission SEND_MESSAGES
     *  - net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     * ### Internal Error
     *  - IllegalThreadStateException
     * @return Void
     * @see net.dv8tion.jda.api.exceptions.InsufficientPermissionException
     * @see IllegalThreadStateException
     * @see java.util.concurrent.RejectedExecutionException
     */
   fun bugged(event: CommandEvent, emoji: String, message: String?) {
       return event.reply("$emoji | ${event.author.asMention}, $message")
   }
    /**
     * getTime
     * Grabs the current time and converts it into a UTC time format
     * @return String?
     * @throws NullPointerException - Internal Error
     * @throws IllegalArgumentException - Internal Error
     */
    fun getTime(): String? {
        val time = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(time)
    }
    fun error(event: CommandEvent, emoji: String, message: String?){
        return event.reply("$emoji | $message")
    }
    fun DJOnly(event: CommandEvent){
        if (event.member?.roles?.stream()?.noneMatch { r: Role -> r.name.equals("DJ", ignoreCase = true) or !event.isOwner or !event.member.hasPermission(Permission.ADMINISTRATOR)}!!) {
            KyroUtils.error(event, "", "")
        }
        }
    fun memoryUsage() {
        val total = Runtime.getRuntime().totalMemory()
        val free = Runtime.getRuntime().freeMemory()
        val used = total - free
        fun converted(): Long {
            val convertedtoKb = used / 1024
            val convertedtomb = convertedtoKb / 1024
            return convertedtomb
        }
     fun unconverted(): Long {
        return used
    }
    }
    fun procUsage(): Double {
        val si = SystemInfo()
        val hal = si.hardware
        val cpu = hal.processor
        var prevTicks = LongArray(TickType.values().size)
        val cpuLoad = cpu.getSystemCpuLoadBetweenTicks(prevTicks) * 100
        prevTicks = cpu.systemCpuLoadTicks
        return cpuLoad
    }
    }






















