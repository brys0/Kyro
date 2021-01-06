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
import brys.org.dev.Command.Owner.*
import brys.org.dev.Command.Settings.*
import brys.org.dev.Command.Settings.GuildSettings.Color
import brys.org.dev.Command.Settings.GuildSettings.DJRole
import brys.org.dev.Command.Settings.GuildSettings.Settings
import brys.org.dev.Kyro.KyroClient
import brys.org.dev.Paginator.method.Pages
import brys.org.dev.Settings.MongoData
import brys.org.dev.Settings.SettingsManager
import brys.org.dev.lib.Util.BotEvents.StatusEvents
import brys.org.dev.lib.Util.GcLogUtil
import brys.org.dev.lib.Util.GuildEvents.GuildJoinEvent
import brys.org.dev.lib.Util.GuildEvents.GuildLeaveEvent
import brys.org.dev.lib.Util.GuildEvents.GuildRegionChangeEvent
import brys.org.dev.lib.Util.Log.LogMethods
import brys.org.dev.lib.Util.cli
import ch.qos.logback.classic.Level
import com.github.ajalt.mordant.TermColors
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory
import io.ktor.utils.io.*
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import java.util.*
import javax.security.auth.login.LoginException
import kotlin.time.ExperimentalTime

import kotlin.Throws



object Booter {
    @ExperimentalTime
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val t = TermColors()
        val waiter = EventWaiter()
        val shardCount = 2 // not compatible..
        val loggerContext = LoggerFactory.getILoggerFactory() as ch.qos.logback.classic.LoggerContext
        val mongolog = loggerContext.getLogger("org.mongodb.driver")
        val jdalog = loggerContext.getLogger("net.dv8tion.jda")
        if (args.contains("-verbose") or args.contains("--v")) {
         println("${t.rgb("#FFFF00").bg} ${t.black}# ${t.reset} - Verbose mode is active.")
            mongolog.level = Level.DEBUG
            jdalog.level = Level.DEBUG
            println("${t.rgb("#FFFF00").bg} ${t.black}# ${t.reset} - Logging GC for lag debug.")
            GcLogUtil.startLoggingGc()
        } else {
            mongolog.level = Level.OFF
            jdalog.level = Level.OFF
        }
        println("${t.brightYellow.bg} ${t.black}~ ${t.reset} - Bot is booting. ")
        LogMethods.Boot
        val client = KyroClient.ClientBuilder
        val settings = SettingsManager()
        client.setPrefix("..")
        client.setAlternativePrefix("<@!750368143209267281>")
        client.setHelpWord("oldhelp")
        client.setOwnerId("443166863996878878")
        client.setCoOwnerIds("286509757546758156", "746166548669923339")
        client.setLinkedCacheSize(10)
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
            Seek(),
            Remove(),
            BassBoost(),
            TrackMv(),
            Skipto(),
            h(),
            Ping(),
            HasteBin(),
            ClearQueue(),
            Join(),
            Leave(),
            Top(),
            Search(waiter),
            Settings(),
            Save(),
            Saved(),
            Color(),
            Reset(),
            DJRole(),
            Pause()
        )
        client.setEmojis("✅", "⚠", "\uD83D\uDED1")
        client.setGuildSettingsManager(settings)
        client.setGuildSettingsManager { guild -> guild }
        LogMethods.Client
        try {
            val builder = JDABuilder.createDefault(
                AUTH["token"],
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_INVITES,
                GatewayIntent.GUILD_BANS
            )
            client.setActivity(Activity.playing("Type -h"))
            builder.addEventListeners(
                waiter,
                client.build(),
                GuildJoinEvent(),
                GuildRegionChangeEvent(),
                GuildLeaveEvent(),
                StatusEvents()
            )
            builder.setAudioSendFactory(NativeAudioSendFactory())
            builder.enableCache(CacheFlag.CLIENT_STATUS)
            LogMethods.Manager
            val api: JDA = builder.build()
            Pages.activate(api)
            cli(api)
        }
        catch (login: LoginException) {
            if (args.contains("-verbose") or args.contains("--v")) {
                 println("${t.red.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Login Exception has occurred, check your Token.${t.reset}"); login.printStackTrace();
                return
            } else
                return println("${t.red.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Login Exception has occurred, check your Token. (To get more info run verbose --v)${t.reset}")
            }
        catch (intent: UnsupportedOperationException) {
            if (args.contains("-verbose") or args.contains("--v")) {
                 println("${t.red.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Illegal Argument Exception has occurred. Check your Gateway Intents, Event Listeners or Cache.${t.reset}"); intent.printStackTrace()
                return
            } else
            return println("${t.red.bg} ${t.black}X ${t.reset} ${t.brightWhite}- Illegal Argument Exception has occurred. Check your Gateway Intents, Event Listeners or Cache. (To get more info run verbose --v)${t.reset}")
        }
    }
}
