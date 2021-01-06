package brys.org.dev.lib.Util

import brys.org.dev.Kyro.Kyro
import brys.org.dev.lib.Util.Log.LogMethods
import com.github.ajalt.mordant.TermColors
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import org.apache.commons.lang3.StringUtils.abbreviate
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*


fun cli(api: JDA) {
        val t = TermColors()
        fun clihelp() {
                          println(t.green("┌──────────────────────────────────────────────CLI───────────────────────────────────────────────┐"))
                          println(" ${t.brightRed}Stats:${t.reset} Brings you the status and amount of guilds for the bot (Additional info coming soon!)   ")
                          println(" ${t.brightRed}Shutdown:${t.reset} Shutdown the bot and finish rest actions                                             ")
                          println(" ${t.brightRed}Restart:${t.reset} Restarts the bot and main process                                                     ")
                          println(" ${t.brightRed}Help:${t.reset} Shows this message again                                                                 ")
                          println(" ${t.brightRed}change-status${t.reset} {status}: Changes the bots status for discord                                    ")
                          println(" ${t.brightRed}oauth:${t.reset} Generates the bots oauth url                                                            ")
                          println(" ${t.brightRed}guild-invite${t.reset} {guildid}: Generates a invite for the guild via id                                ")
                          println(" ${t.brightRed}gc:${t.reset} Calls System.gc() Which makes the jvm attempt to garbage collect whenever convinent        ")
                          println(t.green("└────────────────────────────────────────────────────────────────────────────────────────────────┘"))
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
            if (name == "gc"){
                println("Forcing GC")
                System.gc()
                settings()
            }
            if (name == "stats") {
                println(
                    t.green(
                        "Status: ${api.status}\n" +
                                "Guilds: ${api.guilds.size}, Additional info:\n" +
                                "'Num. of TCS': ${api.textChannels.size}\n" +
                                "'Num. of Audio Managers:' ${api.audioManagers.size}\n"
                    )
                )
                settings()
            }
            if (name == "shutdown") {
                println("Shutting the bot down...")
                Kyro.exit(2000, true, "Shutdown was executed by CLI")
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
                    val invite = api.getGuildById(name.removePrefix("guild-invite").replace(" ", ""))?.channels?.get(1)
                        ?.createInvite()?.setMaxAge(66)?.complete()?.url
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
            if (name.contains("emote")) {
                println("Emote: ${api.emotes} ${api.emoteCache}")
            settings()
        }
            if (name == "gc") {
                println("Forcing GC")
                System.gc()
                settings()
            }
if (name == "export") {
    val f = File("logs.txt")
    val bytes = f.readText()
 val s = abbreviate(bytes, 950)
    api.getGuildById("770757172341506061")?.getTextChannelById("785899078663929866")?.sendMessage("```\n[CLI]\n$s\n```")?.queue()
    settings()
}
        }
        clihelp()
        settings()
    }
