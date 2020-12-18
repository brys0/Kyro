package brys.org.dev.Command.Help.Menu

import brys.org.dev.Command.Help.Menu.Struct.struct
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.entities.MessageEmbed
import brys.org.dev.Paginator.method.Pages
import brys.org.dev.Paginator.model.Page
import brys.org.dev.Paginator.type.PageType
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * Class help menu builds the help command for all other commands using pagination [Pages].
 * And Uses the [struct] to build the predefined command help pages.
 */
class h: Command() {
    override fun execute(event: CommandEvent?) {
        val pagesAl = ArrayList<brys.org.dev.Paginator.model.Page>()
        pagesAl.add(brys.org.dev.Paginator.model.Page(brys.org.dev.Paginator.type.PageType.EMBED, struct.menu.build()))
        pagesAl.add(brys.org.dev.Paginator.model.Page(brys.org.dev.Paginator.type.PageType.EMBED, struct.play.build()))
        pagesAl.add(brys.org.dev.Paginator.model.Page(brys.org.dev.Paginator.type.PageType.EMBED, struct.queue.build()))
        pagesAl.add(brys.org.dev.Paginator.model.Page(brys.org.dev.Paginator.type.PageType.EMBED, struct.skip.build()))
        pagesAl.add(
            brys.org.dev.Paginator.model.Page(
                brys.org.dev.Paginator.type.PageType.EMBED,
                struct.volume.build()
            )
        )
        try {
        event?.channel?.sendMessage(pagesAl[0].content as MessageEmbed)?.queue { success ->
            brys.org.dev.Paginator.method.Pages.paginate(success, pagesAl, 300, TimeUnit.SECONDS, event.author.id)
        }
    } catch (e: Exception) {
        event?.reply("An Internal Error has occurred most likely its an issue with the discord api <https://status.discord.com>")
        }
    }
    init {
        name = "h"
        aliases = arrayOf("help")
        guildOnly = false
    }
}