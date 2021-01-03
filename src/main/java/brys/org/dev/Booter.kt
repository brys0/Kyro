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
import brys.org.dev.Kyro.Kyro
import brys.org.dev.Kyro.KyroClient
import brys.org.dev.Paginator.method.Pages
import brys.org.dev.Settings.SettingsManager
import brys.org.dev.lib.Util.GuildEvents.GuildJoinEvent
import brys.org.dev.lib.Util.GuildEvents.GuildLeaveEvent
import brys.org.dev.lib.Util.GuildEvents.GuildRegionChangeEvent
import brys.org.dev.lib.Util.Log.LogMethods
import brys.org.dev.lib.Util.cli
import com.github.ajalt.clikt.output.TermUi
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
        val t = TermColors()
        val waiter = EventWaiter()
        val shardCount = 2 // not compatible..
        val loggerContext = LoggerFactory.getILoggerFactory() as ch.qos.logback.classic.LoggerContext
        val mongolog = loggerContext.getLogger("org.mongodb.driver")
        val jdalog = loggerContext.getLogger("net.dv8tion.jda")
        val lavaplayer = loggerContext.getLogger("com.sedmelluq")
        mongolog.level = ch.qos.logback.classic.Level.OFF
        jdalog.level = ch.qos.logback.classic.Level.OFF
        println(t.red("Bot is booting on date ${Date()}"))
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
            Reset()
        )
        client.setEmojis("✅", "⚠", "\uD83D\uDED1")
        client.setGuildSettingsManager(settings)
        client.setGuildSettingsManager { guild -> guild }
        LogMethods.Client
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
        builder.addEventListeners(waiter, client.build(), GuildJoinEvent(), GuildRegionChangeEvent(), GuildLeaveEvent())
        builder.setAudioSendFactory(NativeAudioSendFactory())
        builder.enableCache(CacheFlag.CLIENT_STATUS)
        val api: JDA = builder.build()
        Pages.activate(api)
        cli(api)
        LogMethods.Manager
    }
}
