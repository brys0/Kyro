package brys.org.dev.lib.Util.RequiredUtil




import brys.org.dev.Music_Handles.Manager.PlayerManager
import brys.org.dev.Settings.GuildSettings
import brys.org.dev.lib.Util.AudioUtil.AudioChecks.isPlaying
import brys.org.dev.lib.Util.AudioUtil.AudioChecks.memberConnected
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Role

abstract class KyroCommand(): Command() {
        var playingReq: Boolean = false;
        var memberConnectedReq: Boolean = false;
        var djReq: Boolean = false;
        override fun run(event: CommandEvent) {
            val playerManager = PlayerManager.instance
            val GMM = playerManager!!.getGuildMusicManager(event.guild, event.textChannel, event.author)
            val player = GMM.audioPlayer
            if(playingReq && !isPlaying(player)) {
                return event.reply("A track must be playing to use this command.")
            } else if(playingReq && isPlaying(player)) {
                return execute(event);
            }
          if (memberConnectedReq && !memberConnected(event)) {
              return event.reply("You must be connected to a vc to use this command.")
          } else if (memberConnectedReq && memberConnected(event)) {
              return execute(event);
          }
            if (djReq and event.message.member!!.roles.stream().anyMatch{ r: Role -> r.id == GuildSettings.findSetting(event.guild.id)?.get("DjRole")?.toString()}) {
               return execute(event)
            } else if (djReq and event.message.member!!.roles.stream().anyMatch{ r: Role -> r.id != GuildSettings.findSetting(event.guild.id)?.get("DjRole")?.toString()}) {
               return event.reply("A DJ role is required to use this command.")
                }
            execute(event);
        }
    }

