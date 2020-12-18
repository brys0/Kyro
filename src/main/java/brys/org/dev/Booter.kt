/**
 * Copyright (c) 2020
 *Creative Commons.
 * @Attribution
 *You must give attribution to the creator's (Bryson T, B.T) of this work.
 *@Noncommercial
 *This can not be used for commercial use or used to make a profit.
 * @author Brys, B.T
 */

package brys.org.dev

import brys.org.dev.Authenicator.AUTH
import brys.org.dev.Command.Help.Help_Play
import brys.org.dev.Command.Help.Menu.h
import brys.org.dev.Command.Info.Ping
import brys.org.dev.Command.Music.*
import brys.org.dev.Command.Music.Play.play
import brys.org.dev.Command.Music.Queue
import brys.org.dev.Command.Music.Settings.Settings
import brys.org.dev.Command.Owner.*
import brys.org.dev.Kyro.Kyro
import brys.org.dev.Kyro.KyroClient

import brys.org.dev.src.Command.Music.*
import brys.org.dev.src.Command.Owner.*
import brys.org.dev.Paginator.method.Pages
import brys.org.dev.lib.Util.GuildEvents.GuildJoinEvent
import brys.org.dev.lib.Util.GuildEvents.GuildRegionChangeEvent
import brys.org.dev.lib.Util.cli
import com.github.ajalt.mordant.TermColors
import com.jagrosh.jdautilities.commons.JDAUtilitiesInfo
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.JDAInfo
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.slf4j.LoggerFactory
import java.util.*
import javax.security.auth.login.LoginException
import kotlin.time.ExperimentalTime

import kotlin.Throws


object Booter {
    @ExperimentalTime
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        LoggerFactory.getLogger(Booter::class.java)
        System.setProperty("DEBUG.MONGO", "false");
        System.setProperty("DB.TRACE", "false");
        val t = TermColors()
        val waiter = EventWaiter()
        val shardCount = 2 // not compatible..
        println(t.red("Bot is booting on date ${Date()}"))
        println(t.brightBlue("┌──────────────────────────┐"))
        println(t.brightBlue("│          K Y R O         │"))
        println(t.brightBlue("│ Ver: ${Kyro.VERSION}               │"))
        println(t.brightBlue("│ JDA : ${JDAInfo.VERSION}          │"))
        println(t.brightBlue("│ JDA Utilities: ${JDAUtilitiesInfo.VERSION}     │"))
        println(t.brightBlue("└──────────────────────────┘"))
        try {
            val client = KyroClient.ClientBuilder
            println(t.magenta("┌──────────────────────────┐"))
            println(t.magenta("│        C L I E N T       │"))
            client.setPrefix("-")
            client.setAlternativePrefix("<@!750368143209267281>")
            client.setHelpWord("oldhelp")
            println(t.magenta("│ Prefix has been set      │"))
            println(t.magenta("│ Activity has been set    │"))
            client.setOwnerId("443166863996878878")
            client.setCoOwnerIds("286509757546758156", "746166548669923339")
            println(t.magenta("│ Owner has been set.      │"))
            client.setLinkedCacheSize(10)
            println(t.magenta("│ Cache size has been set  │"))
            client.addCommands(
                Skip(),
                Volume(),
                NowPlaying(),
                play(),
                Queue(),
                EvalG(),
                Debug(),
                Help_Play(),
                Repeat(),
                lyric(),
                Settings(),
                Seek(),
                Remove(),
                BassBoost(),
                TrackMv(),
                Skipto(),
                h(),
                Ping(),
                HasteBin(),
                SaveSongDebug(),
                FindSongDebug(),
                ClearQueue(),
                Join(),
                Leave(),
                Top()
            )
            println(t.magenta("│ ${client.build().commands.size} Commands have loaded  │"))
            client.setEmojis("✅", "⚠", "\uD83D\uDED1")
            println(t.magenta("│ Client Loaded!           │"))
            println(t.magenta("└──────────────────────────┘"))
            client.setGuildSettingsManager { guild -> guild }
            val builder = JDABuilder.createDefault(
                brys.org.dev.Authenicator.AUTH["token"],
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.GUILD_PRESENCES,
                            GatewayIntent.GUILD_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_EMOJIS,
                            GatewayIntent.GUILD_INVITES,
                            GatewayIntent.GUILD_BANS)
            println(t.brightRed("┌──────────────────────────┐"))
            println(t.brightRed("│      M A N A G E R       │"))
            println(t.brightRed("│ Gateway Intents loaded   │"))
            println(t.brightRed("│ Status has been loaded   │"))
            println(t.brightRed("│ Activity has been loaded │"))
            client.setActivity(Activity.playing("Type -h"))
            builder.addEventListeners(waiter, client.build(), GuildJoinEvent(), GuildRegionChangeEvent())
            println(t.brightRed("│ Audio Handler Set        │"))
            builder.setAudioSendFactory(NativeAudioSendFactory())
            builder.enableCache(CacheFlag.CLIENT_STATUS)
            println(t.brightRed("│ 3 Listeners loaded       │"))
            println(t.brightRed("│ Cache has been loaded    │"))
            println(t.brightRed("│ Manager Loaded!          │"))
            println(t.brightRed("└──────────────────────────┘"))
            val api: JDA = builder.build()
            Pages.activate(api)
            cli(api)
            println(t.magenta("Logged in as ${api.selfUser.name} seeing: ${api.guildCache.size()} guilds."))
            println(t.magenta("Seeing ${api.guildCache.size()} guilds."))
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
