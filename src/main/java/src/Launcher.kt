/**
 * Copyright (c) 2020
 *Creative Commons.
 * @Attribution
 *You must give attribution to the creator's (Bryson T, B.T) of this work.
 *@Noncommercial
 *This can not be used for commercial use or used to make a profit.
 * @author Brys, B.T
 */

package src


import com.github.ajalt.mordant.TermColors
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import src.Authenicator.AUTH
import src.Command.NowPlaying.NowPlaying
import src.Command.Owner.EvalG
import src.Command.Owner.EvalJ
import src.Command.Play.play
import src.Command.Queue.Queue
import src.Command.Skip.Skip
import src.Command.Volume.Volume
import src.GuildInvite.GuildJoinEvent
import src.OutdatedCommands.`play_outdated!!`
import src.Util.cli
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.security.auth.login.LoginException


object Launcher {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val waiter = EventWaiter()
        val t = TermColors()

        println(t.brightBlue("+--------------------------+"))
        println(t.brightBlue("|          K Y R O         |"))
        println(t.brightBlue("| Ver: 1.0.0               |"))
        println(t.brightBlue("| JDA : 4.2.0_204          |"))
        println(t.brightBlue("| JDA Utilities: 3.0.4     |"))
        println(t.brightBlue("+--------------------------+"))



        try {
            val client = CommandClientBuilder()
            println(t.magenta("+--------------------------+"))
            println(t.magenta("|        C L I E N T       |"))
            client.setPrefix("-")
            client.setAlternativePrefix("<@!750368143209267281>")
            println(t.magenta("| Prefix has been set      |"))
            println(t.magenta("| Activity has been set    |"))
            client.setOwnerId("443166863996878878")
            client.setCoOwnerIds("286509757546758156", "746166548669923339")
            println(t.magenta("| Owner has been set.      |"))
            client.setLinkedCacheSize(10)
            println(t.magenta("| Cache size has been set  |"))
            client.addCommands(Skip(), Volume(), NowPlaying(), play(), Queue(), `play_outdated!!`(), EvalG(), EvalJ())
            println(t.magenta("| Commands have loaded     |"))
            client.setEmojis("✅", "⚠", "\uD83D\uDED1")
            println(t.magenta("| Client Loaded!           |"))
            println(t.magenta("+--------------------------+"))
            val builder = JDABuilder.createDefault(AUTH["token"],
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_PRESENCES,
                    GatewayIntent.GUILD_MESSAGE_REACTIONS,
                    GatewayIntent.GUILD_EMOJIS,
                    GatewayIntent.GUILD_INVITES,
                    GatewayIntent.GUILD_BANS)

            println(t.brightRed("+--------------------------+"))
            println(t.brightRed("|      M A N A G E R       |"))
            println(t.brightRed("| Gateway Intents loaded   |"))
            client.setStatus(OnlineStatus.IDLE)
            println(t.brightRed("| Status has been loaded   |"))
            println(t.brightRed("| Activity has been loaded |"))
            builder.addEventListeners(waiter, client.build(), GuildJoinEvent())
            println(t.brightRed("| Audio Handler Set        |"))
            builder.setAudioSendFactory(NativeAudioSendFactory())
            println(t.brightRed("| Listeners loaded         |"))
            println(t.brightRed("| Cache has been loaded    |"))
            println(t.brightRed("| Manager Loaded!          |"))
            println(t.brightRed("+--------------------------+"))
            val api: JDA? = builder.build()
            if (api != null) {
                cli(api)
            }


        } catch (Ignored: LoginException) {
            println(t.brightYellow("+---------------------------------------------------+"))
            println(t.brightYellow("|                 Launcher Error                    |"))
            println(t.red("| A login exception has occurred in Launcher.Main() |"))
            println(t.red("| ${Ignored.message}                    |"))
            println(t.brightYellow("+---------------------------------------------------+"))
            return
        } catch (err: IllegalArgumentException) {
            println("\uD83D\uDED1" + err.message)
        }
    }
}
