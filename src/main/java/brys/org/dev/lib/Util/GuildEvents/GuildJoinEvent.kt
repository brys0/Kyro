package brys.org.dev.lib.Util.GuildEvents

import brys.org.dev.Settings.GuildSettings
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildJoinEvent: ListenerAdapter() {
    override fun onGuildJoin(event: GuildJoinEvent) {
        event.guild.systemChannel?.sendMessage("Thanks for inviting me!\n" +
                "For help just use `..h` or `..help`")?.queue()
        GuildSettings.guild(event.guild.id)
    }
}