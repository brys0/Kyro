package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role
import java.lang.NumberFormatException

class Remove: Command() {
    override fun execute(event: CommandEvent?) {
        val manager = PlayerManager.instance
        val musicm = event?.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
        val queue = musicm?.scheduler?.queue
val s = musicm?.scheduler
        if (queue.isNullOrEmpty()) {
            event?.reactError()
            event?.reply("There is nothing in this queue!")
            return
        }
        if (event.member.roles.stream().noneMatch { r: Role -> (r.name.equals("DJ", ignoreCase = true) or event.isOwner.not() or event.member.hasPermission(Permission.MESSAGE_MANAGE).not()) }) {
        event.reactError()
            event.reply("You must have the `DJ` Role or have manage messages.")
            return
        }
        val pos: Int = try {
            Integer.parseInt(event.args)
        } catch(e: NumberFormatException) {
            0
        }
if (pos<1 || pos>queue.size) {
    event.reactError()
    event.reply("The position to remove must be in between 1 and ${queue.size}.")
    return
}

  s?.remove(pos)
        event.reply("Removed **#${pos}** from queue")
    }
    init {
        name = "remove"
        aliases = arrayOf("rm","rmv")
        cooldown = 2
        guildOnly = true
    }
}