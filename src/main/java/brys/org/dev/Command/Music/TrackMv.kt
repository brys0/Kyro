package brys.org.dev.Command.Music

import brys.org.dev.Kyro.KyroUtils
import brys.org.dev.Music_Handles.Manager.PlayerManager
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent

class TrackMv: Command() {
    override fun execute(event: CommandEvent) {


        if (event.author.id != event.client.ownerId ) {
            KyroUtils.bugged(event, "<:KyroX:778465200902635560>", "TrackMv is currently in private testing only!")
            return
        } else {
            val manager = PlayerManager.instance
            val musicm = event?.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
            val queue = musicm?.scheduler?.queue
            val s = musicm?.scheduler
            val args = event.args
            val list = queue?.toMutableList()
            try {
                val targeti = args[0].toInt().takeIf { it > 0 }
                    ?: return event.reply("The position must be vaild")
                val desti = args[1].toInt().takeIf { it > 0 && it != targeti }
                    ?: return event.reply("The position must be vaild")

                val selcTrack = list?.get(9)

                list?.removeAt(9)


                list?.add(2, selcTrack)
                queue?.clear()
                queue?.addAll(list!!)
                println("event fired")
                event.reply("Track ${selcTrack?.info?.title} is now at position **${desti}**")
            } catch (e: Exception) {
                event.reply("An exception occured exception: ${e.message}")
            }
        }
            }
    init {
        name = "move"
        aliases = arrayOf("mv","m")
        guildOnly = true

    }
}


