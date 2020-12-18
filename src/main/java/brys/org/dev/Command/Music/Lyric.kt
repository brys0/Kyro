package brys.org.dev.Command.Music

import brys.org.dev.Music_Handles.Manager.PlayerManager
import brys.org.dev.src.Command.Music.Lyric.API.KSoft.searchLyrics
import brys.org.dev.Paginator.method.Pages
import brys.org.dev.Paginator.model.Page
import brys.org.dev.Paginator.type.PageType
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import java.util.*
import java.util.concurrent.TimeUnit


class lyric : Command(){
    override fun execute(event: CommandEvent?) {
        val manager = PlayerManager.instance
        val musicm = event?.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
        val queue = musicm?.scheduler?.queue
        val player = musicm?.audioPlayer
        val s = musicm?.scheduler
        if (!event?.selfMember?.hasPermission(Permission.MESSAGE_MANAGE)!!) {
            event.reactError()
            event.reply("To use my menu manager I need to have the permission node `MANAGE_MESSAGES`")
            return
        }
      if (event.args?.length!! > 2) {
          val manLyrics = searchLyrics(event.args).lyrics
          val pagesMl = ArrayList<brys.org.dev.Paginator.model.Page>()
          val ebM = EmbedBuilder()
          val lyriTetxM = manLyrics?.chunked(2048)
          for (i in 0 until lyriTetxM?.size!!) {
              ebM.clear()
              ebM.setDescription(lyriTetxM[i])
              ebM.setFooter("Page $i of ${lyriTetxM.size} | Requested by: ${event.author.asTag} | Reactions expire in 5 minutes..")
              ebM.setFooter("Page ${i.plus(1)} of ${lyriTetxM.size} | Requested by: ${event.author.asTag} | Reactions expire in 5 minutes.. | Provided by Ksoft API")
              pagesMl.add(brys.org.dev.Paginator.model.Page(brys.org.dev.Paginator.type.PageType.EMBED, ebM.build()))
          }
          event.channel?.sendMessage(pagesMl[0].content as MessageEmbed)?.queue { success -> brys.org.dev.Paginator.method.Pages.paginate(success, pagesMl, 300, TimeUnit.SECONDS, event.author.id) }
          return
      }
        if (player?.playingTrack == null) {
            event.reply("Try again with a playing track.")
            return
        }
        val Lyrics = searchLyrics(player.playingTrack.info.title + "|" + player.playingTrack.info.author)?.lyrics
        val pagesAl = ArrayList<brys.org.dev.Paginator.model.Page>()
        val ebA = EmbedBuilder()
        val lyriTetx = Lyrics?.chunked(2048)
        for (i in 0 until lyriTetx?.size!!) {
            ebA.clear()
            ebA.setTitle(player.playingTrack.info.title, player.playingTrack.info.uri)
            ebA.setDescription(lyriTetx[i])
            ebA.setFooter("Page ${i.plus(1)} of ${lyriTetx.size} | Requested by: ${event.author.asTag} | Reactions expire in 5 minutes.. | Provided by Ksoft API")
            ebA.setThumbnail("https://img.youtube.com/vi/" + player.playingTrack?.info?.uri?.replace("https://www.youtube.com/watch?v=", "") + "/0.jpg")
            pagesAl.add(brys.org.dev.Paginator.model.Page(brys.org.dev.Paginator.type.PageType.EMBED, ebA.build()))
        }
        event.channel?.sendMessage(pagesAl[0].content as MessageEmbed)?.queue { success ->  brys.org.dev.Paginator.method.Pages.paginate(success, pagesAl, 300, TimeUnit.SECONDS, event.author.id)
        }
    }
    init {
        name = "lyric"
        aliases = arrayOf("lyrics", "l", "lyr")
        cooldown = 10
        cooldownScope = CooldownScope.GUILD
        arguments = getArguments()
        guildOnly = false

    }
}