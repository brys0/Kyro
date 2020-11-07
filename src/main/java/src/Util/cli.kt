package src.Util

import com.github.ajalt.mordant.TermColors
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import src.Launcher
import java.io.BufferedReader
import java.io.InputStreamReader


    fun cli(api: JDA) {
        val t = TermColors()


        fun clihelp() {
            println(t.green("+-----------Command Line Interface Options-------------------------------------------------------+"))
            println(t.green("| 'Stats': Brings you the status and amount of guilds for the bot (Additional info coming soon!) |"))
            println(t.green("| 'Shutdown': Shutdown the bot and finish rest actions                                           |"))
            println(t.green("| 'Restart': Restarts the bot and main process                                                   |"))
            println(t.green("| 'Help': Shows this message again                                                               |"))
            println(t.green("| 'change-status {status}': Changes the bots status for discord                                  |"))
            println(t.green("| 'oauth': Generates the bots oauth url                                                          |"))
            println(t.green("| 'guild-invite {guildid}' Generates a invite for the guild via id                               |"))
            println(t.green("+------------------------------------------------------------------------------------------------+"))
        }

        fun settings() {
            val reader = BufferedReader(InputStreamReader(System.`in`))
            val name = reader.readLine()
            if (name == " ") {
                settings()
            }
            if (name == "") {
                settings()
            }
            if (name == "stats") {
                println(t.green("Status: ${api.status}\n" +
                        "Guilds: ${api.guilds.size}, Additional info:\n" +
                        "'Num. of TCS': ${api.textChannels.size}\n" +
                        "'Num. of Audio Managers:' ${api.audioManagers.size}\n"))
                settings()
            }
            if (name == "shutdown") {
                println("Shutting the bot down...")
                api.shutdown()

            }
            if (name == "restart") {
                println("Restarting...")
                api.shutdown()
            }
            if (name == "help") {
                clihelp()
                settings()
            }
            if (name.contains("change-status")) {
                if (name.removePrefix("change-status").isEmpty()) {
                    println("This command can not be blank!")
                    settings()
                }
                println("Changing status to: ${name.removePrefix("change-status")}")
                api.selfUser.jda.presence.setPresence(Activity.playing(name.removePrefix("change-status")), false)
                api.selfUser.jda.presence.setPresence(OnlineStatus.ONLINE, false)
                settings()
            }
            if (name.contains("oauth")) {
                val oauth = api.getInviteUrl()
                println("Bot invite generated: $oauth")
                settings()
            }
            if (name.contains("guild-invite")) {
                if (name.removePrefix("guild-invite").length < 16) {
                    println("No id supplied")
                    settings()
                }
                try {
                    val invite = api.getGuildById(name.removePrefix("guild-invite").replace(" ", ""))?.channels?.get(1)?.createInvite()?.setMaxAge(66)?.complete()?.url
                    println("Guild invite: $invite")
                    settings()
                } catch (e: java.lang.Exception) {
                    println("Something went wrong trying to create your invite.")
                    settings()
                }
            }
            if (name.contains("clear")) {
                print("\\033[H\\033[2J")
                settings()
            }
            if (name.contains("message")) {
                if (name.removePrefix("message").length < 2) {
                    println("message required")
                    settings()
                }
            }
            if (name.contains("emote"))
                println("Emote: ${api.emotes} ${api.emoteCache}")
                settings()
        }
        clihelp()
        settings()
    }
