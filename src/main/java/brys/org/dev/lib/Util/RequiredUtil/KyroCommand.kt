package brys.org.dev.lib.Util.RequiredUtil




import brys.org.dev.Music_Handles.Manager.PlayerManager
import brys.org.dev.lib.Util.AudioUtil.AudioChecks.isPlaying
import brys.org.dev.lib.Util.AudioUtil.AudioChecks.memberConnected
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
    abstract class KyroCommand(): Command() {
        var playingReq: Boolean = false;
        var memberConnectedReq: Boolean = false;
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
            execute(event);
        }
    }

