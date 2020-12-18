package brys.org.dev.lib.Util.GuildEvents

import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.update.GuildUpdateRegionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildRegionChangeEvent: ListenerAdapter() {
    override fun onGuildUpdateRegion(event: GuildUpdateRegionEvent) {
        event.guild.systemChannel?.sendMessage("Server region changed give me a second to reconnect.\n" +
                "${event.oldRegion.emoji} ${event.oldRegion} -> ${event.newRegion.emoji} ${event.newRegion}")?.queue()
    }
    }
